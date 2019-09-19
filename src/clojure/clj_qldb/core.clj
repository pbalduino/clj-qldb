(ns clj-qldb.core
  (:import (pbalduino.cljqldb.helper Connection)
           (software.amazon.qldb PooledQldbDriver
                                 QldbSession)))

(defn create-driver [^String ledger-name ^Integer retries]
  {:pre [(some? ledger-name)]}
  (Connection/createQldbDriver ledger-name retries))

(defn create-session [^PooledQldbDriver driver]
  (.getSession driver))

(defn get-table-names [^QldbSession session]
  (-> session
      .getTableNames
      .iterator
      iterator-seq))

; create table

; update

; insert

; delete

; select
