(ns clj-data-analysis.chap10.customjfreecharts
  (require [incanter.core :as i]
           [incanter.charts :as c]
           incanter.datasets)
  (import org.jfree.chart.renderer.category.LayeredBarRenderer
          org.jfree.util.SortOrder))


(def iris (incanter.datasets/get-dataset :iris))

(def iris-petal-scatter
  (c/scatter-plot (i/sel iris :cols :Petal.Width)
                  (i/sel iris :cols :Petal.Length)
                  :title "Irises: Petal Width by Petal Length"
                  :x-label "Width (cm)"
                  :y-label "Length (cm)"))

(def iris-dimensions
  (i/with-data
    iris
    (doto (c/bar-chart :Species :Petal.Width
                       :title "iris' dimensions"
                       :x-label "species"
                       :y-label "cm"
                       :series-label "petal width"
                       :legend true)
      (c/add-categories
       :Species :Sepal.Width
       :series-label "sepal width")
      (c/add-categories
       :Species :Petal.Length
       :series-label "petal length")
      (c/add-categories
       :Species :Sepal.Length
       :series-label "sepal length"))))

(doto (.getPlot iris-dimensions)
  (.setRenderer (doto (LayeredBarRenderer.)
                  (.setDrawBarOutline false)))
  (.setRowRenderingOrder SortOrder/DESCENDING))

(i/view iris-dimensions)
