(ns clj-data-analysis.chap04.pmap)

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

(defn output-points
  ([max-x max-y]
   (let [range-y (range max-y)]
     (mapcat (fn [x] (map #(vector x %) range-y))
             (range max-x)))))

(defn mandelbrot-pixel
  ([max-x max-y max-iterations set-range]
   (partial mandelbrot-pixel
            max-x max-y max-iterations set-range))
  ([max-x max-y max-iterations set-range
    [pixel-x pixel-y]]
   (let [[x y] (scale-point pixel-x pixel-y max-x max-y set-range)]
     (get-escape-point x y max-iterations))))

(defn mandelbrot
  ([mapper max-iterations max-x max-y set-range]
   (doall
    (mapper (mandelbrot-pixel
             max-x max-y max-iterations set-range)
            (output-points max-x max-y)))))

(def mandelbrot-range
  {:x [-2.5 1.0] :y [-1.0 1.0]})

;; run it
(def m (time (mandelbrot map 500 1000 1000
                         mandelbrot-range)))

(def m (time (mandelbrot pmap 500 1000 1000
                         mandelbrot-range)))

(def m (time (mandelbrot map 1000 1000 1000
                         mandelbrot-range)))

(def m (time (mandelbrot pmap 1000 1000 1000
                         mandelbrot-range)))
