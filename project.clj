(defproject clj-data-analysis "0.1.0-SNAPSHOT"
  :description "Examples from Clojure Data Analysis Cookbook"
  :url "http://dawillah.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 ;;                  [incanter/incanter-core "1.5.1"]
                 ;;                  [incanter/incanter-io "1.5.1"]
                 ;;                  [incanter/incanter-charts "1.5.1"]
                 [incanter "1.5.1"]
                 [org.clojure/data.json "0.2.5"]
                 [clj-diff "1.0.0-SNAPSHOT"]
                 [clj-time "0.9.0-beta1"]
                 [org.clojure/data.csv "0.1.2"]
                 [protoflex/parse-ez "0.4.2"]
                 [valip "0.2.0"]
                 [criterium "0.4.3"]
                 [calx "0.2.1"]
                 [cascalog "1.10.0"]
                 [org.slf4j/slf4j-api "1.7.2"]
                 ]
  :repositories [["conjars.org" "http://conjars.org/repo"]]
  :profiles
  {:dev
   {:dependencies
    [[org.apache.hadoop/hadoop-core "1.1.1"]]}}
  )
