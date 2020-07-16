(ns black-jack.core)

(defn start-game? []
  (if (= (read-line) "go") true false))

(def BLACK-JACK 21)

(defn user-wins []
  (prn "CONGRATULATIONS, YOU WON!"))

(defn user-loses []
  (prn "OH NO, YOU LOST!"))

(defn create-deck []
  (->> (range 2 11)
       (concat ["A" "J" "Q" "K"])
       (repeat 4)
       (reduce concat)
       vec))

(defn give-cards [deck]
  (vec (repeatedly 2 #(rand-nth deck))))

(defn calculate-value-of-card [card]
  (case card
    "A" 2
    "K" 10
    "Q" 10
    "J" 10
    card))

; pure function
(defn calculate-result [cards]
  (let [values (map #(calculate-value-of-card %) cards)]
    (reduce + values)))

; pure function
(defn create-result-object [user-cards new-card]
  (let [all-cards (conj user-cards new-card)
        result (calculate-result all-cards)]
    {:result      result
     :cards       all-cards
     :end-of-game (if (>= result BLACK-JACK) true false)}))

(defn show-cards-and-result [result-object]
  (prn "Your cards are:")
  (loop [i 0]
    (when (< i (count (:cards result-object)))
      (prn (nth (:cards result-object) i))
      (recur (inc i))))
  (prn (str "Score: " (:result result-object))))

; TODO: user cards should be subtracted from the deck
(defn give-card [user-cards deck]
  (rand-nth deck))

(defn ask-for-action []
  (prn "What do you want to do next? 1. Get another card! (Other values will end the game)")
  (read-line))

(defn show-new-card [new-card]
  (prn (str "Your new card is: " new-card)))

(defn end-game-with [result]
  (if (= BLACK-JACK result)
    (user-wins)
    (user-loses)))

(defn translate-result-for-user [result-object]
  (show-cards-and-result result-object)
  result-object)

(defn execute-round [user-cards]
  (->> (create-deck)
       (give-card user-cards)
       (create-result-object user-cards)
       (translate-result-for-user)))

(defn play-round [result-object]
  (let [action (ask-for-action)]
    (if (= action "1")
      (execute-round (:cards result-object))
      (end-game-with (:result result-object)))))

;(defn play []
;  (let [deck (create-deck)
;        user-card (give-card [] deck)
;        result-of-round-one (execute-round [user-card])]
;    (if (:end-of-game result-of-round-one)
;      (end-game-with (:result result-of-round-one))
;      (let [result-object (play-round result-of-round-one)]
;        (loop [result (:result result-of-round-one)]
;          (when (or (> result 0) (< result 21))
;            (prn "result " result)
;            (prn "You can continue!"))
;          (recur (+ (- result (:result result-object)) result)))))))

(defn play []
  (let [deck (create-deck)
        user-card (give-card [] deck)
        result-of-round-one (execute-round [user-card])
        result-of-round-two (if (:end-of-game result-of-round-one)
                              (end-game-with (:result result-of-round-one))
                              (play-round result-of-round-one))]
    (if (:end-of-game result-of-round-two)
      (end-game-with (:result result-of-round-two))
      (play-round result-of-round-two))))

(defn interface []
  (prn "Welcome to Black Jack!")
  (prn "If you want to start the game, type 'go'")
  (if (start-game?) (play) (prn "Bye, see you next time!"))
  (prn "Wanna play again? - type 'go'")
  (if (start-game?) (play) (prn "Bye, see you next time!")))