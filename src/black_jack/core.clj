(ns black-jack.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import (java.io PushbackReader)))

(defn get-deck [file]
  (when-let [url (or (io/resource file) (io/file file))]
    (with-open [r (-> url io/reader PushbackReader.)]
      (edn/read r))))

(defn create-game-context [deck]
  {:deck     deck
   :player-1 (repeatedly 2 #(rand-nth deck))})

(defn play []
  (->> (get-deck "deck.edn")
       (create-game-context)))

(defn start-game? []
  (if (= (read-line) "go") true false))

(defn interface []
  (prn "Welcome to Black Jack!")
  (prn "If you want to start the game, type 'go'")
  (if (start-game?) (play) (prn "Bye, see you next time!"))
  #_(prn "Wanna play again? - type 'go'")
  #_(if (start-game?) (play) (prn "Bye, see you next time!")))