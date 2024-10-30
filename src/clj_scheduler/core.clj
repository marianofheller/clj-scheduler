(ns clj-scheduler.core
  (:gen-class)
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.logger :as logger]
            [clojure.tools.logging :as log]
            [clj-scheduler.jobs :as jobs]
            [clj-scheduler.scheduler :as scheduler]
            [clj-scheduler.api.http :refer [web-app]]))

(defn ring-log-fn [{level :level message :message}]
  (log/log level nil message))

(defn schedule-jobs []
  (scheduler/schedule-job jobs/println-job jobs/example-1-trigger))

(defn -main
  [& _args]
  (schedule-jobs)
  (run-jetty
   (logger/wrap-with-logger web-app {:log-fn ring-log-fn})
   {:port 3000}))

