(ns clj-data-analysis.chap03.validators
  (:use
   clj-data-analysis.helpers
   [clj-data-analysis.chap02.lazylarge :only [lazy-read-csv]]
   [clj-data-analysis.chap03.stm :only [with-header ->int sum-item sum-items thunk-update-totals-for data-file]]
   [clj-data-analysis.chap03.agent :only [accum-sums div-vec force-val]]))

;; Convert these rows to int
(def int-rows
  [:GEOID :SUMLEV :STATE :POP100 :HU100 :POP100.2000
   :HU100.2000 :P035001 :P035001.2000])

(defn int?
  [x] (or (instance? Integer x) (instance? Long x)))

(defn try-read-string
  "attempts to read string to an integer"
  [x]
  (try
    (read-string x)
    (catch Exception ex x)))

(defn coerce-row
  "Converts all whole number fields to int values"
  [_ row sink]
  (let [cast-row (apply assoc row
                        (mapcat (fn [k]
                                  [k (try-read-string (k row))])
                                int-rows))]
    (send sink conj cast-row)
    cast-row))

(defn read-row
  "Reads the input. Sends an item of input to coerce-row agent, queues itself to read
  another item on input and sets its value to the rest of the input"
  [rows caster sink]
  (when-let [[item & items] (seq rows)]
    (send caster coerce-row item sink)
    (send *agent* read-row caster sink)
    items))

(defn int-val?
  "Validator"
  [x]
  (or (int? x) (empty? x)))

(defn validate
  "Do the validation"
  [row]
  (or (nil? row)
      (reduce #(and %1 (int-val? (%2 row)))
              true int-rows)))

(defn agent-ints
  "Agent definitions, starts processing and returns them"
  [input-file]
  (let [reader (agent (seque
                       (with-header
                         (lazy-read-csv
                          input-file))))
        caster (agent nil)
        sink (agent [])]
    (set-validator! caster validate)
    (send reader read-row caster sink)

    {
     :reader reader
     :caster caster
     :sink sink
     }))

;; Run it
(def ags (agent-ints data-file))
(first @(:sink ags))
