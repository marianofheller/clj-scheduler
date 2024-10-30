(ns clj-scheduler.api.http
  (:require [ring.middleware.multipart-params :refer (wrap-multipart-params)]
            [ring.middleware.multipart-params.byte-array :refer (byte-array-store)]
            [ring.middleware.defaults :refer (wrap-defaults, site-defaults)]
            [compojure.core :as c]
            [compojure.route :as route]))

(defn transform-input-page []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "HOME")})

(c/defroutes main-routes
  (c/GET "/" [] (transform-input-page))
  (route/resources "/")
  (route/not-found "Page not found"))

(def web-app
  (-> (wrap-defaults main-routes site-defaults) ;; (handler/site main-routes)
      (wrap-multipart-params {:store (byte-array-store)})))
