(ns clj-scheduler.scheduler
  (:require [clojurewerkz.quartzite.scheduler :as qs]))


(def scheduler (memoize (fn [] (-> (qs/initialize) qs/start))))

(defn schedule-job [job trigger] (qs/schedule (scheduler) job trigger))

(defn unschedule-job [trigger-key] (qs/delete-trigger (scheduler) trigger-key))

(defn pause-job [trigger-key] (qs/pause-job (scheduler) trigger-key))

(defn resume-job [trigger-key] (qs/resume-job (scheduler) trigger-key))

