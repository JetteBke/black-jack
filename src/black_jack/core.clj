(ns black-jack.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import (java.io PushbackReader)))

(def BLACK-JACK 21)

(defn get-deck [file]
  (when-let [url (or (io/resource file) (io/file file))]
    (with-open [r (-> url io/reader PushbackReader.)]
      (edn/read r))))

(defn end-game [game-context]
  (if (= (reduce + (vals (:player-1 game-context))) BLACK-JACK)
    (prn "You won! Black Jack!")
    (prn "You lost with " (reduce + (vals (:player-1 game-context))))))

;(defn give-card? [game-context]
;  (prn "Your cards are " (map #(name %) (keys (:player-1 game-context))) "with result " (:result game-context))
;  (prn "Type '1' to get a new card")
;  (if (= (read-line) "1") true false))

(defn create-game-context [deck]
  (let [player-card-one (rand-nth deck)
        player-cards (conj player-card-one (rand-nth (filter #(not= (key (first %)) (key (first player-card-one))) deck)))]
    {:deck     deck
     :player-1 player-cards}))

(defn deal [game-context]
  (let [remaining-deck (filter #(not (contains? (vec (keys (:player-1 game-context))) %))
                               (:deck game-context))
        player-cards (conj (:player-1 game-context) (rand-nth remaining-deck))]
    {:deck     remaining-deck
     :player-1 player-cards}))

;(defn play-round [game-context]
;  (if (give-card? game-context)
;    (deal game-context)
;    game-context))

(defn play [game-context]
  (loop [current-game-context game-context]
    (if (>= (reduce + (vals (:player-1 current-game-context))) BLACK-JACK)
      (end-game current-game-context)
      (recur
        (do (prn "Your cards are " (map #(name %)
                                        (keys (:player-1 current-game-context)))
                 "with result " (reduce + (vals (:player-1 current-game-context))))
            (deal current-game-context))))))

(defn start-game []
  (->> (get-deck "deck.edn")
       (create-game-context)
       (play)))

;(defn start-game? []
;  (if (= (read-line) "go") true false))

(defn interface []
  (prn "Welcome to Black Jack! Your game will start now...")
  (start-game))