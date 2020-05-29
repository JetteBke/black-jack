(ns black-jack.core)

(defn start-game? []
  (if (= (read-line) "go") true false))

(def BLACK-JACK 21)

(defn user-wins []
  (prn "CONGRATULATIONS, YOU WON!"))

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

(defn calculate-result [cards]
  (let [values (map #(calculate-value-of-card %) cards)]
    (reduce + values)))

(defn show-cards-and-result [user-cards result]
  (prn (str "Your cards are: " (list user-cards)))
  (prn (str "Score: " result)))

(defn give-card [deck user-cards]
  (rand-nth deck))

(defn ask-for-action []
  (prn "What do you want to do next? 1. Get another card!, 2. End!")
  (read-line))

(defn show-new-card [new-card]
  (prn (str "Your new card is: " new-card)))

(defn play-second-round [user-cards result-of-round-one deck]
  (if (= "1" (ask-for-action))
    (let [new-card (give-card deck user-cards)
          user-cards-after-round-two (conj user-cards new-card)
          result-after-round-two (calculate-result user-cards-after-round-two)]
      (show-new-card new-card)
      (show-cards-and-result user-cards-after-round-two result-after-round-two))))

(defn play []
  (let [deck (create-deck)
        user-cards (give-cards deck)
        result-of-round-one (calculate-result user-cards)]
    (show-cards-and-result user-cards result-of-round-one)
    (if (= result-of-round-one BLACK-JACK)
      (user-wins)
      (play-second-round user-cards result-of-round-one deck))))

(defn interface []
  (prn "Welcome to Black Jack!")
  (prn "If you want to start the game, type 'go'")
  (if (start-game?) (play))
  ;(prn "Wanna play again? - type 'go'")
  ;(if (start-game?) (play))
  )