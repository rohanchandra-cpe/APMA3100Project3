library(tidyverse)

output <- read_table2("output.txt", 
                      col_names = FALSE, 
                      skip = 4, 
                      n_max = 880,
                      col_names = c('sampleSize', 'm'))

output = output %>% rename(sampleSize = X1,
                  m = X2)

output %>% 
  mutate(sampleSize = factor(sampleSize)) %>% 
  ggplot(aes(sampleSize, m, col = sampleSize)) + geom_point()

output %>% 
  ggplot(aes(sampleSize, m, col = sampleSize)) + geom_point()
