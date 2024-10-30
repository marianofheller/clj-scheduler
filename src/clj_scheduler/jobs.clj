(ns clj-scheduler.jobs
  (:require [clojurewerkz.quartzite.jobs :as j]
            [clojurewerkz.quartzite.triggers :as t]
            [clojure.tools.logging :as log]
            [clj-http.client :as client]
            [clojurewerkz.quartzite.schedule.simple :as simple])

  (:import [org.quartz JobExecutionException]))

(defn throw-retry
  ([] (throw (JobExecutionException. "retry exception" true)))
  ([e] (throw (JobExecutionException. "retry exception" e true))))

(j/defjob FetchStuffJob
  [_ctx]
  (client/get
   "https://api.chess.com/pub/leaderboards"
   {:async? true}
   (fn [response] (log/info "response is:" response))
   (fn [exception] (log/error "exception message is: " (.getMessage exception)))))

(def println-job (j/build
                  (j/of-type FetchStuffJob)
                  (j/with-identity (j/key "jobs.println.1"))))

(def example-1-tk      (t/key "triggers.1"))

(def example-1-trigger (t/build
                        (t/with-identity example-1-tk)
                        (t/start-now)
                        (t/with-schedule (simple/schedule
                                          (simple/with-repeat-count 10)
                                          (simple/with-interval-in-milliseconds 2000)))))