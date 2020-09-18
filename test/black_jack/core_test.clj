(ns black-jack.core-test
  (:require [clojure.test :refer :all]
            [black-jack.core :as core :refer :all]
            [mock-clj.core :as mock]))

(def a-deck [{:heart-2 2}
             {:diamond-2 2}
             {:spade-2 2}
             {:club-2 2}
             {:heart-3 3}
             {:diamond-3 3}
             {:spade-3 3}
             {:club-3 3}
             {:heart-4 4}
             {:diamond-4 4}
             {:spade-4 4}
             {:club-4 4}
             {:heart-5 5}
             {:diamond-5 5}
             {:spade-5 5}
             {:club-5 5}
             {:heart-6 6}
             {:diamond-6 6}
             {:spade-6 6}
             {:club-6 6}
             {:heart-7 7}
             {:diamond-7 7}
             {:spade-7 7}
             {:club-7 7}
             {:heart-8 8}
             {:diamond-8 8}
             {:spade-8 8}
             {:club-8 8}
             {:heart-9 9}
             {:diamond-9 9}
             {:spade-9 9}
             {:club-9 9}
             {:heart-10 10}
             {:diamond-10 10}
             {:spade-10 10}
             {:club-10 10}
             {:heart-king 10}
             {:heart-queen 10}
             {:heart-jack 10}
             {:heart-ace 11}
             {:diamond-king 10}
             {:diamond-queen 10}
             {:diamond-jack 10}
             {:diamond-ace 11}
             {:spade-king 10}
             {:spade-queen 10}
             {:spade-jack 10}
             {:spade-ace 11}
             {:club-king 10}
             {:club-queen 10}
             {:club-jack 10}
             {:club-ace 11}])

(deftest create-deck-test
  (testing "should create a deck with the numbers 2-10, A, J, Q, K for all four colors"
    (is (= a-deck (core/get-deck "deck.edn")))))

(deftest create-game-context-test
  (testing "should return a game context with deck and player cards"
    ; returning only one card because i didn't find a way to return two different maps from the mock
    (mock/with-mock [rand-nth {:heart-2 2}]
                    (is (= {:deck     a-deck
                            :player-1 {:heart-2 2}} (core/create-game-context a-deck))))))

(deftest deal-test
  (testing "should create a new game context with updated deck and values"
    (mock/with-mock [rand-nth {:diamond-king 10}]
                    (let [old-context {:deck     a-deck
                                       :player-1 {:heart-5 5 :heart-4 4}}]
                      (is (= {:deck     a-deck
                              :player-1 {:heart-5      5
                                         :heart-4      4
                                         :diamond-king 10}} (core/deal old-context)))))))





