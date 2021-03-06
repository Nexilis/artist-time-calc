(ns artist-time-calc.core-test
  (:require [clojure.test :refer :all]
            [artist-time-calc.conf :refer :all]
            [artist-time-calc.init :refer :all]
            [artist-time-calc.rand :refer :all]
            [artist-time-calc.core :as c]))

; TODO: work on DRY - https://clojure.github.io/clojure/clojure.test-api.html
(def test-config (calculate-config))

(defn- reduce-column-to-sum [calendar column]
  (reduce (fn [accumulator day] (+ accumulator (day column)))
          0 ;; accumulator's initial value
          calendar))

(deftest sum-of-worked-hours
  (testing "Given default configuration when random calendar is created then sum of worked hours is correct"
    (let [calendar (c/build-calendar test-config)
          expected-total-wr-h (-> :sum-of-worked-hours-in-period test-config)
          actual-total-cr-h (+ (reduce-column-to-sum calendar :cr-h)
                               (reduce-column-to-sum calendar :not-cr-h))]
      (is (= expected-total-wr-h actual-total-cr-h)))))

(deftest sum-of-copyrighted-hours
  (testing "Given default configuration when random calendar is created then sum of copyrighted hours is correct"
    (let [calendar (c/build-calendar test-config)
          expected-total-cr-h (-> :sum-of-copyrighted-hours-in-period test-config)
          actual-total-cr-h (reduce-column-to-sum calendar :cr-h)]
      (is (= expected-total-cr-h actual-total-cr-h)))))

(deftest sum-of-not-copyrighted-hours
  (testing "Given default configuration when random calendar is created then sum of not-copyrighted hours is correct"
    (let [calendar (c/build-calendar test-config)
          expected-total-not-cr-h (- (-> :sum-of-worked-hours-in-period test-config)
                                     (-> :sum-of-copyrighted-hours-in-period test-config))
          actual-total-not-cr-h (reduce-column-to-sum calendar :not-cr-h)]
      (is (= expected-total-not-cr-h actual-total-not-cr-h)))))