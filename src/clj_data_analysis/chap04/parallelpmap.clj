(ns clj-data-analysis.chap04.parallelpmap)

(defn get-escape-point
  "Takes a point and the maximum number of iterations to try and return the
  iteration that it escapes on. Value gets above 4"
  [scaled-x scaled-y max-iterations]
  (loop [x 0 y 0 iteration 0]
    (let [x2 (* x x)
          y2 (* y y)]
      (if (and (< (+ x2 y2) 4)
               (< iteration max-iterations))
        (recur (+ (- x2 y2) scaled-x)
               (+ (* 2 x y) scaled-y)
               (inc iteration))
        iteration))))

(defn scale-to
  ""
  [pixel maximum [lower upper]]
  (+ (* (/ pixel maximum)
        (Math/abs (- upper lower))) lower))

(defn scale-point
  ""
  [pixel-x pixel-y max-x max-y set-range]
  [(scale-to pixel-x max-x (:x set-range))
   (scale-to pixel-y max-y (:y set-range))])
