(ns clj-qldb.core
  (:import (pbalduino.cljqldb.helper Connection)
           (software.amazon.qldb PooledQldbDriver
                                 QldbSession)))

(defn create-driver
  ([^String ledger-name]
    (create-driver ledger-name 5))
  ([^String ledger-name ^Integer retries]
    {:pre [(some? ledger-name)]}
    (Connection/createQldbDriver ledger-name retries)))

(defn get-session [^PooledQldbDriver driver]
  (.getSession driver))

(defn create-session
  ([^String ledger-name]
    (create-session ledger-name 5))
  ([^String ledger-name ^Integer retries]
    (get-session (Connection/createQldbDriver ledger-name retries))))

(defn get-table-names [^QldbSession session]
  (-> session
      .getTableNames
      .iterator
      iterator-seq))

(defn create-table [_ _])

(defn insert [_ _ _])

; delete

; select
