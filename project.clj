(defproject pbalduino/clj-qldb "0.0.1-SNAPSHOT"
  :description "AWS QLDB + Clojure"
  :url "https://github.com/pbalduino/clj-qldb"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :scm {:name "git"
        :url "https://github.com/pbalduino/clj-qldb"}
  :source-paths ["src"]
  :deploy-repositories [["clojars" {:url "https://repo.clojars.org"}
                        ["releases" :clojars]
                        ["snapshots" :clojars]]]
  :dependencies [[pbalduino/dandelion "0.1.0"]]
  :plugins [[lein-cljfmt "0.6.4"]]
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[clj-kondo "RELEASE"]
                                  [org.clojure/clojure "1.10.0"]]
                   :aliases {"clj-kondo" ["run" "-m" "clj-kondo.main"]}}})