(ns black-jack.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import (java.io PushbackReader)))

(def BLACK-JACK 21)

(defn get-deck [file]
  (when-let [url (or (io/resource file) (io/file file))]
    (with-open [r (-> url io/reader PushbackReader.)]
      (edn/read r))))

(defn deal [game-context]
  (let [remaining-deck (filter #(not (contains? (vec (keys (:player-1 game-context))) %))
                               (:deck game-context))]
    {:deck     remaining-deck
     :player-1 (conj (:player-1 game-context) (rand-nth remaining-deck))}))

(defn create-game-context [deck]
  (let [player-card-one (rand-nth deck)]
    {:deck     deck
     :player-1 (conj player-card-one (rand-nth (filter #(not= (key (first %)) (key (first player-card-one))) deck)))}))

(defn play []
  (->> (get-deck "deck.edn")
       (create-game-context)
       (deal)))

(defn start-game? []
  (if (= (read-line) "go") true false))

(defn interface []
  (prn "Welcome to Black Jack!")
  (prn "If you want to start the game, type 'go'")
  (if (start-game?) (play) (prn "Bye, see you next time!"))
  #_(prn "Wanna play again? - type 'go'")
  #_(if (start-game?) (play) (prn "Bye, see you next time!")))