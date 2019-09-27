(defproject pbalduino/clj-qldb "0.1.3"
  :description "A lightweight library to use AWS QLDB in Clojure"
  :url "https://github.com/pbalduino/clj-qldb"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :scm {:name "git"
        :url "https://github.com/pbalduino/clj-qldb"}
  :deploy-repositories [["clojars" {:url "https://repo.clojars.org"}
                        ["releases" :clojars]
                        ["snapshots" :clojars]]]
  :dependencies [[pbalduino/dandelion "0.1.1"]
                 [software.amazon.qldb/amazon-qldb-driver-java "1.0.1"]]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :plugins [[lein-cljfmt "0.6.4"]]
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[clj-kondo "RELEASE"]
                                  [org.clojure/clojure "1.10.0"]
                                  [org.slf4j/slf4j-nop "1.7.26"]]
                   :aliases {"clj-kondo" ["run" "-m" "clj-kondo.main"]}}})
