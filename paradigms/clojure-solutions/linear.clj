(defn sameSizeVector? [vs]
  (every? (fn [v] (== (count v) (count (first vs)))) vs))

(defn numbers? [vs]
  (if (vector? vs)
    (every? true? (mapv numbers? vs))
    (number? vs)))

(defn numberVectors? [vs]
  (every? (fn [v] (every? number? v)) vs))

(defn numberMatrix? [m]
  (numberVectors? m))

(defn vf [f]
  (fn [& vs]
  {:pre [(every? vector? vs) (sameSizeVector? vs) (numberVectors? vs)]}
  (apply mapv f vs)))

(def v+
  (vf +))

(def v-
  (vf -))

(def v*
  (vf *))

(def vd
  (vf /))

(defn scalar [& vs]
  {:pre [(every? vector? vs) (numberVectors? vs)]}
  (reduce + (apply v* vs)))
(defn vect [& vs]
  {:pre [(every? vector? vs) (numberVectors? vs) (every? (fn [v] (== (count v) 3)) vs)]}
  (reduce (fn [a b]
            [(- (* (nth a 1) (nth b 2)) (* (nth a 2) (nth b 1)))
             (- (* (nth a 2) (nth b 0)) (* (nth a 0) (nth b 2)))
             (- (* (nth a 0) (nth b 1)) (* (nth a 1) (nth b 0)))
             ]) vs))
(defn v*s [v & ss]
  {:pre [(vector? v) (every? number? v) (every? number? ss)]}
  (let [sres (apply * ss)]
    (mapv (fn [crd] (* crd sres)) v
          )))

(defn matrix? [m]
  (and (vector? m) (every? vector? m) (sameSizeVector? m)))

(defn sameSizeMatrix? [ms]
  (and (sameSizeVector? ms) (every? (fn [m] (== (count (first m)) (count (first (first ms))))) ms)))

(defn mf [f]
  (fn [& ms]
  {:pre [(every? matrix? ms) (sameSizeMatrix? ms)]}
  (apply mapv f ms)))

(def m+
  (mf v+))
(def m-
  (mf v-))
(def m*
  (mf v*))
(def md
  (mf vd))

(defn m*s [m & ss]
  {:pre [(matrix? m) (numberMatrix? m) (every? number? ss)]}
  (let [sres (apply * ss)]
    (mapv (fn [v] (v*s v sres)) m)))

(defn m*v [m v]
  {:pre [(matrix? m) (vector? v)]}
  (mapv (fn [mv] (scalar mv v)) m))

(defn transpose [m]
  {:pre [(matrix? m) (numberMatrix? m)]}
  (apply mapv vector m))

(defn goodSizes? [ms]
  (if (== (count ms) 1)
    (identity true)
    (and (== (count (first (first ms))) (count (first (rest ms))))
         (recur (rest ms)))
    ))
(defn m*m [& ms]
  {:pre [(every? matrix? ms) (every? numberMatrix? ms) (goodSizes? ms)]}
  (reduce (fn [lst m] (mapv (fn [v1] (mapv (fn [v2] (scalar v1 v2)) (transpose m)))lst)) ms))

(defn simplex? [x]
  (cond
    (empty? x) false
    (every? number? x) true
    (not (every? vector? x)) false
    (== (count (first x)) (count x)) (and (simplex? (first x))
                                          (if (== (count x) 1)
                                            true
                                            (simplex? (rest x))))
    :else false))

(defn sameSizeSimplex? [xs]
  (true?
    (letfn [(fun [& vs]
              (if (every? number? vs)
                (identity true)
                (and (sameSizeVector? vs) (every? true? (apply map fun vs))))
              )]
      (apply fun xs))))

(defn xf [f] (fn [& xs]
  {:pre [(every? simplex? xs) (sameSizeSimplex? xs)]}
  (letfn [(fun [& vs]
            (if (every? number? vs)
              (apply f vs)
              (apply mapv fun vs)))]
    (apply fun xs))
  ))

(def x+
  (xf +))

(def x-
  (xf -))

(def x*
  (xf * ))

(def xd
  (xf /))
