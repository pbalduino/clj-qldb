(ns clj-qldb.core
  (:require [clojure.string :as str]
            [dandelion.core :as ion])
  (:import (pbalduino.cljqldb.helper Driver)
           (java.util ArrayList
                      List
                      Map)
           (software.amazon.qldb ExecutorNoReturn
                                 PooledQldbDriver
                                 QldbSession
                                 TransactionExecutor)))

(defn create-driver
  ([^String ledger-name]
   (create-driver ledger-name 5))
  ([^String ledger-name ^Integer retries]
   {:pre [(some? ledger-name)]}
   (Driver/createQldbDriver ledger-name retries)))

(defn get-session [^PooledQldbDriver driver]
  (.getSession driver))

(defn create-session
  ([^String ledger-name]
   (create-session ledger-name 5))
  ([^String ledger-name ^Integer retries]
   (get-session (Driver/createQldbDriver ledger-name retries))))

(defn- ->clj-list [ion-list]
  (map ion/ion->clj
       (-> ion-list
           .iterator
           iterator-seq)))

(defn get-table-names [^QldbSession session]
  (-> session
      .getTableNames
      .iterator
      iterator-seq))

(defn- execute
  ([^QldbSession session ^String statement]
   (->clj-list (.execute session statement)))
  ([^QldbSession session ^String statement ^List data]
   (->clj-list (.execute session statement data))))

(defn create-table [^QldbSession session ^String table]
  (execute session (format "CREATE TABLE %s" table)))

(defn drop-table [^QldbSession session ^String table]
  (execute session (format "DROP TABLE %s" table)))

(defn insert [^QldbSession session ^String table ^Map data]
  (let [ion-list (ArrayList.)]
    (.add ion-list (ion/clj->ion data))
    (execute session (format "INSERT INTO %s ?" table) ion-list)))

(defn select [^QldbSession session ^String table & opts]
  (let [{:keys [fields filter]} (merge {:fields nil
                                        :filter nil}
                                       (apply hash-map opts))
        field-str (if fields (str/join "," fields) "*")
        condition (if filter (str " WHERE " filter) "")
        query (format "SELECT %s FROM %s%s" field-str table condition)]
    (execute session query)))
