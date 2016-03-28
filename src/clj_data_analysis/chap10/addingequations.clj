(ns clj-data-analysis.chap10.addingequations
  (require [incanter.core :as i]
           [incanter.charts :as c]
           [incanter.latex :as latex]))
(def f-plot
  (c/function-plot
    #(/ 1.0 (Math/log %)) 0.0 1.0
    :title "Inverse log function."
    :y-label "Inverse log"))

;; We bind the LaTeX string to the name inv-log.
(def inv-log "f(x)=\\frac{1.0}{\\log x}")

(latex/add-latex-subtitle f-plot inv-log)

(i/view f-plot)
