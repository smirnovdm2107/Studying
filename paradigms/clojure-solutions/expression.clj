(defn div
  ([x] (if (== x 0)
         ##Inf
         (/ 1 x)))
  ([x & xs] (apply * x (mapv div xs))))

(defn constant [n]
  (constantly n))

(defn variable [name]
  (fn [vars]
    (vars name)))

(defn operation [f]
  (fn [& args]
    (fn [vars]
      (apply f (mapv (fn [expr] (expr vars)) args)))))

(def add (operation +))

(def multiply
  (operation *))

(def subtract
  (operation -))

(def divide
  (operation div))
(def negate
  (operation -))

(defn calcMean [& args] (/ (apply + args) (count args)))

(defn calcVar [& args]
  (let [tmean (apply calcMean args)]
    (- (apply calcMean (mapv (fn [x] (* x x)) args))
       (* tmean tmean))))

(def mean
  (operation calcMean))

(def varn
  (operation calcVar))

(def SIGN_TO_OP {
                 "+"      add
                 "-"      subtract
                 "*"      multiply
                 "/"      divide
                 "negate" negate
                 "mean"   mean
                 "varn"   varn})

(defn parse [expr]
  (cond
    (list? expr) (let [op (SIGN_TO_OP (name (first expr)))
                       args (mapv parse (rest expr))]
                   (apply op args))
    (number? expr) (constant expr)
    (symbol? expr) (variable (name expr))))

(defn parseFunction [string]
  (parse (read-string string)))

\\ \\ \\ \\ \\ \\ \\ \\
(load-file "proto.clj")

(def _args (field :args))

(def _f (field :f))

(def _arg (field :arg))

(def _var (field :var))

(def diff (method :diff))

(def _dfun (field :dfun))

(def _sign (field :sign))

(def toString (method :toString))

(def evaluate (method :evaluate))

(def toStringInfix (method :toStringInfix))

(def constConstr (fn [this val]
                   (assoc this :val val)))

(declare ConstZERO)

(def ConstProto {
                 :toString (fn [this] (str (:val this)))
                 :diff     (fn [this var] ConstZERO)
                 :evaluate (fn [this vars] (:val this))
                 :toStringInfix (fn [this] (str (:val this)))
                 })

(def Constant (constructor ConstProto constConstr))


(def ConstZERO (Constant 0))

(def ConstONE (Constant 1))

(def VariableProto {
                    :toString (fn [this] (:var this))
                    :diff     (fn [this v] (if (= v (:var this))
                                             ConstONE
                                             ConstZERO))
                    :evaluate (fn [this vars] (get vars (str (Character/toLowerCase (first (:var this)))))
                                )
                    :toStringInfix (fn [this] (:var this))
                    })

(def variableConstr (fn [this var]
                      (assoc this :var var)))

(def Variable (constructor VariableProto variableConstr))

;constructor #(assoc %0 :args args) (constructor #(assoc #0 ;3 fields;) ExpressionProto)
(defn binaryOperationConstr [this f sign dfun & args]
  (assoc this
    :sign sign
    :f f
    :dfun dfun
    :args args))
(def OperationProto
  {:toString (fn [this]
               (let [strs (mapv (fn [x] (toString x)) (_args this))]
                 (str "(" (:sign this) " " (clojure.string/join " " strs) ")")))
   :toStringInfix (fn [this]
                    (let [first (toStringInfix (first (_args this)))
                          second (toStringInfix (last (_args this)))]
                      (str "(" first " " (_sign this) " " second ")")))
   :evaluate (fn [this vars]
               (apply (:f this) (mapv (fn [x] (evaluate x vars)) (_args this))))
   :diff (fn [this var] ((_dfun this) (_args this) (mapv #(diff % var) (_args this))))
   })
(def BinaryOperation (constructor OperationProto binaryOperationConstr))

(defn makeOperation [proto f sign diff]
  (constructor proto (fn [this & args]
                       (apply binaryOperationConstr this f sign diff args))))

(def NegateProto
  {
   :proto OperationProto
   :toStringInfix (fn [this] (str "negate(" (toStringInfix (first (_args this))) ")"))
   })

(def Negate (makeOperation NegateProto - "negate" (fn [as ds]
                                                    (Negate (first ds)))))

(declare Add)

(defn add-diff [as ds]
  (apply Add ds))

(def Add
  (makeOperation OperationProto + "+" add-diff))

(declare Subtract)

(defn subtract-diff [as ds]
  (apply Subtract ds))

(def Subtract
  (makeOperation OperationProto - "-" subtract-diff))

(declare Multiply)

(defn multiply-diff [as ds]
  (if (< 1 (count as))
    (Add (apply Multiply (first ds) (rest as))
         (Multiply (first as) (multiply-diff (rest as) (rest ds))))
    (first ds)))

(def Multiply
  (makeOperation OperationProto * "*" multiply-diff))

(declare Divide)

(defn divide-diff [as ds]
  (if (< 1 (count as))
    (let [num (first as)
          num' (first ds)
          den (apply Multiply (rest as))
          den' (multiply-diff (rest as) (rest ds))]
      (Divide (Subtract (Multiply num' den) (Multiply num den'))
              (Multiply den den)))
    (divide-diff [ConstONE (first as)] [ConstZERO (first ds)])))

(def Divide
  (makeOperation OperationProto div "/" divide-diff))

(declare Mean)

(defn mean-diff [as ds]
  (apply Mean ds))

(def Mean (makeOperation OperationProto calcMean "mean" mean-diff))

(defn varn-diff [as ds]
  (let [tmean (apply Mean as)
        tmean' (apply Mean ds)
        first (apply Mean (apply mapv Multiply [as ds]))
        second (Multiply tmean tmean')]
    (Multiply (Constant 2) (Subtract first second))
    ))

(def Varn (makeOperation OperationProto calcVar "varn" varn-diff))


(defn calcPow [a b]
  (Math/pow a b))

(defn pow-diff [as ds]
  nil)

(def IPow (makeOperation OperationProto calcPow "**" pow-diff))

(defn calcLog [a b]
  (/ (Math/log  (Math/abs b)) (Math/log (Math/abs a))))

(defn log-diff [as ds]
  nil)

(def ILog (makeOperation OperationProto calcLog "//" log-diff))

(def SIGN_TO_OBJ
  {"+"      Add
   "-"      Subtract
   "*"      Multiply
   "/"      Divide
   "negate" Negate
   "mean"   Mean
   "varn"   Varn
   "**" IPow
   "//" ILog
   })


(defn parseExpression [expr]
  (cond
    (list? expr) (let [op (SIGN_TO_OBJ (name (first expr)))
                       args (mapv (fn [x] (parseExpression x)) (rest expr))]
                   (apply op args))
    (number? expr) (Constant expr)
    :else (Variable (name expr))))

(defn parseObject [string]
  (parseExpression (read-string string)))

;\\\\\\\\\\ PART III \\\\\\\\\\

(load-file "parser.clj")

(defn -show [r]
  (if (-valid? r)
    (str "-> " (pr-str (-value r)) " | " (pr-str (apply str(-tail r))))
    "!"))

(defn -tabulate [p & inputs]
  (run! (fn [input] (printf "   %-10s %s\n" (pr-str input) (-show (p input)))) inputs))


(def all-chars (mapv char (range 0 128)))

(def letter (+char (apply str (filter #(Character/isLetter %) all-chars))))

(def digit (+char (apply str (filter #(Character/isDigit %) all-chars))))

(def space (+char (apply str (filter #(Character/isWhitespace %) all-chars))))

(def ws (+ignore (+star space)))

(def letter-seq (+plus letter))

(def +variable (+map Variable (+str letter-seq)))

(defn sign [s & tail]
  (if (#{\- \+} s)
    (cons s tail)
    tail))

(def number (+map read-string (+str (+map flatten (+seqf sign (+opt (+char "+-")) (+plus digit)
                                                         (+opt  (+char ".")) (+star digit))))))

(def +const (+map Constant number))

(defn +chars
  [pre] (apply +seq  (mapv #(+char (str %)) pre)))

(declare +parentheses +negate)

(def +argument (+or  #'+parentheses #'+negate +variable +const))

(def +negate (+seqf Negate (+ignore (+chars "negate")) ws #'+argument))

(defn *sign [p & ps]
  (if (empty? ps)
    p
    (+or p (apply *sign ps))))

(defn parse-op [sign]
  (+map SIGN_TO_OBJ (+str (+chars sign))))

(def parse-add (parse-op "+"))
(def parse-subtract (parse-op "-"))
(def parse-multiply (parse-op "*"))
(def parse-divide (parse-op "/"))
(def parse-pow (parse-op "**"))
(def parse-log (parse-op "//"))

(defn infixToPrefix [a op b]
  (op a b))

(defn right-fold
  ([a op & args] (op a (apply right-fold args)))
  ([a] a))

(defn left-fold
  ([a op b & args] (apply left-fold (op a b) args))
  ([a] a))


(def ASSOCIATIVITY
  {parse-add  left-fold
   parse-subtract left-fold
   parse-multiply left-fold
   parse-divide left-fold
   parse-pow right-fold
   parse-log right-fold
   })

(def priority-order
  [
   [parse-add parse-subtract]
   [parse-multiply parse-divide]
   [parse-pow parse-log]
   ])

(defn *op [p & ps]
  (let [fold-type (ASSOCIATIVITY (first p))
        arg-parse (+or (if (< 0 (count ps))
                         (apply *op ps)
                         +argument)
                       +argument)]
    (+map #(apply fold-type %) (+map flatten (+seq arg-parse (+star (+seq ws (apply +or p) ws arg-parse)))))))

(def expression (+seqn 0 ws (apply *op priority-order) ws))

(def +parentheses (+seqn 1 (+char "(") ws expression ws (+char ")")))

(def parseObjectInfix (+parser expression))
