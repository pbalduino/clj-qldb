(ns clj-qldb.core
  (:import (pbalduino.cljqldb.helper Connection)))

(defn create-connection [ledger-name retries]
  (Connection/createQldbDriver ledger-name retries))

(defn create-session [conn]
  (.getSession conn))

(defn get-table-names [session]
  (.getTableNames session))

; create table

; update

; insert

; delete

; select
