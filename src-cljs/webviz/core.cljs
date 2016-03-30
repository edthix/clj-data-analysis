(ns webviz.core)

(defn ^:export hello [world]
  (js/alert (str "Hello, " world)))
