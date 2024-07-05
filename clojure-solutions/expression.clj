(load-file "proto.clj")
(load-file "parser.clj")

(defn universalOp [op] (fn [& operation]
                         (fn [args]
                           (apply op (mapv #(% args) operation)))))

(def negate (universalOp #(- %)))
(def add (universalOp +))
(def subtract (universalOp -))
(def multiply (universalOp *))
(def myDiv #(/ (double %1) (double %2)))
(def divide (universalOp (fn [& args] (if (= 1 (count args))
                                        (myDiv 1 (first args))
                                        (reduce myDiv args)))))

(def constant constantly)
(def variable #(fn [args] (args %)))

(def sin (universalOp #(Math/sin %)))
(def cos (universalOp #(Math/cos %)))

(def operation {'negate negate
                '+      add
                '-      subtract
                '*      multiply
                '/      divide
                'sin    sin
                'cos    cos})

(defn parseTokens [objMap constants vars] #(cond
                                             (number? %) (constants %)
                                             (list? %) (apply (get objMap (first %)) (map (parseTokens objMap constants vars) (rest %)))
                                             :else (vars (str %))))
(def parseFunction #((parseTokens operation constant variable) (read-string %)))

;======================================================================================================

(def toStringPostfix (method :toStringPostfix))
(def toString (method :toString))
(def evaluate (method :evaluate))
(def getX (field :x))
(def getY (field :y))

(defn myProto [eval string stringPostfix]
  {:evaluate eval :toString string :toStringPostfix stringPostfix})

(def constFabric (constructor (fn [this x] (assoc this :x x))
                              (myProto
                                (fn [this vars] (if (number? (getX this)) (getX this) (vars (str (Character/toLowerCase (first (getX this)))))))
                                (fn [this] (str (getX this)))
                                (fn [this] (str (getX this)))
                                )))

(defn fabric [calc symbol] (constructor (fn [this x y] (assoc this :x x :y y))
                                           (myProto
                                             (fn [this vars] (calc (evaluate (getX this) vars) (evaluate (getY this) vars)))
                                             (fn [this] (str "(" symbol " " (toString (getX this)) " " (toString (getY this)) ")"))
                                             (fn [this] (str "(" (toStringPostfix (getX this)) " " (toStringPostfix (getY this)) " " symbol ")"))
                                             )))

(defn unFabric [calc symbol] (constructor (fn [this x] (assoc this :x x))
                                             (myProto
                                               (fn [this vars] (calc (evaluate (getX this) vars)))
                                               (fn [this] (str "(" symbol " " (toString (getX this)) ")"))
                                               (fn [this] (str "(" (toStringPostfix (getX this)) " " symbol ")"))
                                               )))

(def Constant constFabric)
(def Variable constFabric)
(def Add (fabric + '+))
(def Subtract (fabric - '-))
(def Multiply (fabric * '*))
(def Divide (fabric myDiv '/))
(def Negate (unFabric - 'negate))

(def Exp (unFabric #(Math/exp %) 'exp))
(def Ln (unFabric #(Math/log %) 'ln))

(def Operation {'negate Negate
                '+      Add
                '-      Subtract
                '*      Multiply
                '/      Divide
                'exp    Exp
                'ln     Ln})

(def parseObject #((parseTokens Operation Constant Variable) (read-string %)))

;======================================================================================================

(declare <expression>)

(def <space> (+char " \t\n\r"))
(def <ws> (+ignore (+star <space>)))
(def <digit> (+char "0123456789."))
(defn <sign> [c tail]
  (if (#{\-} c)
    (cons c tail)
    tail))

(def +ic #(+ignore (+char %)))
(def +spc #(+str (+plus (+char %))))

(def <number> (+str (+seqf <sign>
                           (+opt (+char "-"))
                           (+plus <digit>))))
(def <var> (+spc "XYZxyz"))
(def <op> (+spc (reduce str
                        (mapv name
                              (keys Operation)))))

(def <constant> (+map (comp Constant read-string) <number>))
(def <variable> (+map (comp Variable str) <var>))
(def <operation> (+map (comp (fn [x] (Operation (symbol x))) read-string) <op>))

(def <tokens> (+map first
                    (+seq
                      <ws>
                      (delay (+or <variable> <constant> <expression>))
                      <ws>)
                    ))

(def <expression> (+seqf #(apply %2 %1)
                         (+ic "(")
                         <ws>
                         (+star <tokens>)
                         <ws>
                         <operation>
                         <ws>
                         (+ic ")")
                         ))

(def parseObjectPostfix (+parser <tokens>))
