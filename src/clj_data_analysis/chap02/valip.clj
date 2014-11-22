(ns clj-data-analysis.chap02.valip
  (:use valip.core
        valip.predicates))


;; Get some data
(def user
  {
   :given-name "Kenshin"
   :surname "Himura"
   :age 28
   :badge "RK1"
   })

(defn number-present?
  "Check if number is present"
  [x]
  (and (present? (str x))
       (or (instance? Integer x)
           (instance? Long x))))


(defn valid-badge
  "Badge must be AAA111"
  [n]
  (not (nil? (re-find #"[A-Z]{3}\d+" n))))

(defn validate-user
  [user]
  (validate user
            [:given-name present? "Given name required."]
            [:surname present? "Surname required."]
            [:age number-present? "Age required."]
            [:age (over 0) "Age should be +"]
            [:age (under 150) "Age should be under 150"]
            [:badge present? "The badge number is required."]
            [:badge valid-badge "The badge is invalid."]
  ))


;; Test our validation
(validate-user user)
(validate-user (assoc user :badge "KEN1868"))
(validate-user (assoc user :surname ""))
