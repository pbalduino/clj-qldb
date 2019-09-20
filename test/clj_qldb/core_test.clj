(ns clj-qldb.core-test
  (:require [clojure.test :as t]
            [clj-qldb.core :refer [create-session
                                   create-table
                                   drop-table
                                   get-table-names
                                   insert
                                   select]]))

(defn rand-str [len]
  (apply str (take len (repeatedly #(char (+ (rand 26) 65))))))

(t/deftest clj-qldb-test
  (t/testing "Pre conditions"
    (t/is (some? (System/getenv "QLDB_LEDGER_NAME")) "You must provide a ledger name via env var")
    (t/is (some? (System/getenv "AWS_REGION")) "You must provide the AWS region via env var"))

  (t/testing "QLDB"
    (let [ledger-name (System/getenv "QLDB_LEDGER_NAME")
          session (create-session ledger-name)
          table-name (rand-str 10)
          id (rand-str 32)
          other-id (rand-str 32)]

      (t/testing "Create table"
        (create-table session table-name)
        (t/is (some #{table-name} (get-table-names session))
              (format "Table '%s' was not created" table-name)))

      (t/testing "Insert data"
        (insert session table-name {:id id
                                    :name "Jeremias"
                                    :age 42})
        (insert session table-name {:id other-id
                                    :name "Humberto"
                                    :age 20}))

      (t/testing "Querying data"
        (t/testing "with selected fields"
          (let [result (select session table-name :fields ["id" "name"])]
            (t/is (= (count result) 2))
            (doseq [row result]
              (t/is (= (count (keys row)) 2))
              (t/is (= '("id" "name")
                       (keys row))))))

        (t/testing "without parameters"
          (let [result (select session table-name)]
            (t/is (= (count result) 2))
            (doseq [row result]
              (t/is (= (count (keys row)) 3))
              (t/is (= '("id" "name" "age")
                       (keys row))))))

        (t/testing "with greater than"
          (let [result (select session table-name :filter "age > 21")]
            (t/is (= (count result) 1))
            (let [row (first result)]
              (t/is (= (count (keys row)) 3))
              (t/is (= '("id" "name" "age")
                       (keys row)))
              (t/is (= (get row "name") "Jeremias")))))

        (t/testing "with less fix than"
          (let [result (select session table-name :filter "age < 21")]
            (t/is (= (count result) 1))
            (let [row (first result)]
              (t/is (= (count (keys row)) 3))
              (t/is (= '("id" "name" "age")
                       (keys row)))
              (t/is (= (get row "name") "Humberto")))))

        (t/testing "with a function and a filter"
          (let [result (select session table-name :fields ["count(*) as c"] :filter "age < 21")]
            (t/is (= (count result) 1))
            (let [row (first result)]
              (t/is (= (count (keys row)) 1))
              (t/is (= '("c")
                       (keys row)))
              (t/is (= (get row "c") 1)))))

        (t/testing "with a function only"
          (let [result (select session table-name :fields ["count(*) as c"])]
            (t/is (= (count result) 1))
            (let [row (first result)]
              (t/is (= (count (keys row)) 1))
              (t/is (= '("c")
                       (keys row)))
              (t/is (= (get row "c") 2))))))

      (t/testing "Drop table"
        (drop-table session table-name)
        (t/is (not (some #{table-name} (get-table-names session)))
              (format "Table '%s' was not removed" table-name))))))
