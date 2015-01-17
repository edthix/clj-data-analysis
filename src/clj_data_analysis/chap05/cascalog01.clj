(ns clj-data-analysis.chap05.cascalog01
  (:require (cascalog [workflow :as w] [ops :as c] [vars :as v])
            [clojure.string :as string])
  (:use cascalog.api
        [clj-data-analysis.chap05.companions]))

(?<- (stdout) [?companion] (companion ?companion))

(.printStackTrace *e)
