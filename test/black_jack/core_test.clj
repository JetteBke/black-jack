(ns black-jack.core-test
  (:require [clojure.test :refer :all]
            [black-jack.core :as core :refer :all]))

(def a-deck [{:heart-2 2}
             {:diamond-2 2}
             {:spade-2 2}
             {:cross-2 2}
             {:heart-3 3}
             {:diamond-3 3}
             {:spade-3 3}
             {:cross-3 3}
             {:heart-4 4}
             {:diamond-4 4}
             {:spade-4 4}
             {:cross-4 4}
             {:heart-5 5}
             {:diamond-5 5}
             {:spade-5 5}
             {:cross-5 5}
             {:heart-6 6}
             {:diamond-6 6}
             {:spade-6 6}
             {:cross-6 6}
             {:heart-7 7}
             {:diamond-7 7}
             {:spade-7 7}
             {:cross-7 7}
             {:heart-8 8}
             {:diamond-8 8}
             {:spade-8 8}
             {:cross-8 8}
             {:heart-9 9}
             {:diamond-9 9}
             {:spade-9 9}
             {:cross-9 9}
             {:heart-10 10}
             {:diamond-10 10}
             {:spade-10 10}
             {:cross-10 10}
             {:heart-:K 10}
             {:heart-:Q 10}
             {:heart-:J 10}
             {:heart-:A 11}
             {:diamond-:K 10}
             {:diamond-:Q 10}
             {:diamond-:J 10}
             {:diamond-:A 11}
             {:spade-:K 10}
             {:spade-:Q 10}
             {:spade-:J 10}
             {:spade-:A 11}
             {:cross-:K 10}
             {:cross-:Q 10}
             {:cross-:J 10}
             {:cross-:A 11}])

(deftest create-deck-test
  (testing "should create a deck with the numbers 2-10, A, J, Q, K for all four colors"
    (is (= a-deck (core/create-deck)))))

(deftest give-card-test
  (testing "should take a random card from the deck"
    (is (= true (map? (core/give-card a-deck))))))

(deftest calculate-result-test
  (testing "should calculate value of user cards"
    (is (= 20 (core/calculate-result [{:diamond-8 8} {:cross-9 9} {:spade-3 3}])))))