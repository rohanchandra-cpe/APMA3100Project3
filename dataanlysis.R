library(tidyverse)

output <- read_table2("output.txt", 
                      col_names = FALSE, 
                      skip = 4, 
                      n_max = 880,
                      col_names = c("sampleSize", "mean"))
