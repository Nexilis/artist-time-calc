(ns artist-time-calc.core-test
  (:require [clojure.test :refer :all]
            [artist-time-calc.conf :refer :all]
            [artist-time-calc.init :refer :all]
            [artist-time-calc.rand :refer :all]
            [artist-time-calc.core :as c]))

; TODO: work on DRY - https://clojure.github.io/clojure/clojure.test-api.html

(defn- reduce-column-to-sum [calendar column]
  (reduce (fn [accumulator day] (+ accumulator (day column)))
          0 ;; accumulator's initial value
          calendar))

(deftest sum-of-worked-hours
  (testing "Given default configuration when random calendar is created then sum of worked hours is correct"
    (let [calendar (c/build-calendar)
          expected-total-wr-h (-> :wr-total-h c/config)
          actual-total-cr-h (+ (reduce-column-to-sum calendar :cr-h)
                               (reduce-column-to-sum calendar :not-cr-h))]
      (is (= expected-total-wr-h actual-total-cr-h)))))

(deftest sum-of-copyrighted-hours
  (testing "Given default configuration when random calendar is created then sum of copyrighted hours is correct"
    (let [calendar (c/build-calendar)
          expected-total-cr-h (-> :cr-total-h c/config)
          actual-total-cr-h (reduce-column-to-sum calendar :cr-h)]
      (is (= expected-total-cr-h actual-total-cr-h)))))

(deftest sum-of-not-copyrighted-hours
  (testing "Given default configuration when random calendar is created then sum of not-copyrighted hours is correct"
    (let [calendar (c/build-calendar)
          expected-total-not-cr-h (- (-> :wr-total-h c/config) (-> :cr-total-h c/config))
          actual-total-not-cr-h (reduce-column-to-sum calendar :not-cr-h)]
      (is (= expected-total-not-cr-h actual-total-not-cr-h)))))