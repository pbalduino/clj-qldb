(ns clj-qldb.core-test
  (:require [clojure.test :as t]
            [clj-qldb.core :refer [create-driver
                                   create-session
                                   get-table-names]]))

(t/deftest clj-qldb-test
  (t/testing "Pre conditions"
    (t/is (some? (System/getenv "QLDB_LEDGER_NAME")) "You must provide a ledger name via env var")
    (t/is (some? (System/getenv "AWS_REGION")) "You must provide the AWS region via env var"))

  (t/testing "Connection"
    (let [ledger-name (System/getenv "QLDB_LEDGER_NAME")
          driver (create-driver ledger-name 5)
          session (create-session driver)
          tables (get-table-names session)]
      (t/is (seq? (iterator-seq (.iterator tables)))))))
