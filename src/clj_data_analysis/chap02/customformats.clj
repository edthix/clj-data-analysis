(ns clj-data-analysis.chap02.customformats
  (:use protoflex.parse))

;; FASTA format
;; http://en.wikipedia.org/wiki/FASTA_format

(defn <|
  "Parse two things and throw away the results of the second."
  [l r]
  (let [l-output (l)]
    (r)
    l-output))

(defn nl
  "Parser for end of a line"
  []
  (chr-in #{\newline \return}))

(defn defline
  "Parses the sequence definition by accepting a '>'
  character followed by anything up to the end of the line."
  []
  (chr \>)
  (<| #(read-to-re #"[\n\r]+") nl))

(defn acid-code
  "Sequence of amino or nucleic acid codes"
  []
  (chr-in #{\A \B \C \D \E \F \G \H \I \K \L \M \N
            \P \Q \R \S \T \U \V \W \X \Y \Z \- \*}))

(defn acid-code-line
  []
  (<| #(multi+ acid-code) #(attempt nl)))

(defn fasta
  "Combines all parsers to parse an entire FASTA record
  and populates a map with our data"
  []
  (ws?)
  (let [dl (defline)
        gls (apply str
                   (flatten (multi+ acid-code-line)))]
    {:define dl :gene-seq gls}))

(def fasta-data
  ">gi|5524211|gb|AAD44166.1| cytochrome b [Elephas maximus maximus]
LCLYTHIGRNIYYGSYLYSETWNTGIMLLLITMATAFMGYVLPWGQMSFWGATVITNLFSAIPY
IGTNLVEWIWGGFSVDKATLNRFFAFHFILPFTMVALAGVHLTFLHETGSNNPLGLTSDSDKIP
FHPYYTIKDFLGLLILILLLLLLALLSPDMLGDPDNHMPADPLNTPLHIKPEWYFLFAYAILRS
VPNKLGGVLALFLSIVILGLMPFLHTSKHRSMMLRPLSQALFWTLTMDLLTLTWIGSQPVEYPY
TIIGQMASILYFSIILAFLPIAGXIENY")

(defn parse-fasta
  [input]
  (parse fasta input :eof false :auto-trim false))

;; Time to test

(parse-fasta fasta-data)


