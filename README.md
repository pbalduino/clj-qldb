# clj-qldb

[![Clojars Project](https://img.shields.io/clojars/v/pbalduino/clj-qldb.svg)](https://clojars.org/pbalduino/clj-qldb)

[![cljdoc badge](https://cljdoc.org/badge/pbalduino/clj-qldb)](https://cljdoc.org/d/pbalduino/clj-qldb/CURRENT)

## What?
`clj-qldb` is a small and lightweight library that allows you to use AWS [QLDB](https://aws.amazon.com/qldb/) without having to care about Java code, proprietary formats and all those distracting and boring things.

You send Clojure data and receive Clojure data, without wasting time.

## Why?

From [QLDB description](https://aws.amazon.com/qldb/):

> Amazon QLDB is a fully managed ledger database that provides a transparent, immutable, and cryptographically verifiable transaction log ‎owned by a central trusted authority. Amazon QLDB tracks each and every application data change and maintains a complete and verifiable history of changes over time.

## How?

### Lein/Boot
```
[pbalduino/clj-qldb "0.1.3"]
```

### deps.edn
```
pbalduino/clj-qldb {:mvn/version "0.1.3"}
```

### Sample

```clojure
;; The ledger name is a String. Here we're getting it from the environment.
;; For QLDB the ledger is like a database in the SQL world.
(def ledger-name (System/getenv "QLDB_LEDGER_NAME"))

;; Session is the gateway between this library and the service. You'll use it all the time.
(def session (create-session ledger-name))

;; Another string containing the table name. It's analog to a SQL table.
(def table-name "SAMPLE_TABLE")

(create-table session table-name)

;; Insert two rows:
(insert session table-name {:id "IUGBLMEAMHYGPNARTUOISSAZLYCTYDOL"
                            :name "Jeremias"
                            :age 42})

(insert session table-name {:id "TUPTVXWNBVJNOQLYFHOFWJWWEGKTTXTW"
                            :name "Humberto"
                            :age 20})

;; Query data:
(select session table-name :fields ["id" "name"])
; ({"id" "IUGBLMEAMHYGPNARTUOISSAZLYCTYDOL", "name" "Humberto"}
;  {"id" "TUPTVXWNBVJNOQLYFHOFWJWWEGKTTXTW", "name" "Jeremias"})

(select session table-name)
;({"id" "IUGBLMEAMHYGPNARTUOISSAZLYCTYDOL",
;  "name" "Humberto",
;  "age" 20}
; {"id" "TUPTVXWNBVJNOQLYFHOFWJWWEGKTTXTW",
;  "name" "Jeremias",
;  "age" 42})

(select session table-name :filter "age > 21")
; ({"id" "TUPTVXWNBVJNOQLYFHOFWJWWEGKTTXTW",
;   "name" "Jeremias",
;   "age" 42})

(select session table-name :filter "age < 21")
; ({"id" "IUGBLMEAMHYGPNARTUOISSAZLYCTYDOL",
;   "name" "Humberto",
;   "age" 20})

(select session table-name :fields ["count(*) as c"] :filter "age < 21")
; ({"c" 1})

(select session table-name :fields ["count(*) as c"])
; ({"c" 2})

;; Of course you don't need to create and drop tables all the time in the real world
(drop-table session table-name)
```

## Who?
[Plínio Balduino](https://github.com/pbalduino), software developer and problem solver.
