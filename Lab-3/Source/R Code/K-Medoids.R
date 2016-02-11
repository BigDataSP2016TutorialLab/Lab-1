##########################
##
##  K-medoids clustering
##
##########################


###########################################
###  Cars example 
###########################################


# Consider the cars.data and cars.std data frames we created above.

# Let's cluster the cars into k groups using the K-medoids approach.

# The function "pam" is in the "cluster" package.

# Loading the "cluster" package:

library(cluster)

# K-medoids directly on the (standardized) data matrix:
cars.kmed.3 <- pam(cars.std, k=3, diss=F)

# Or you can do K-medoids by inputting the distance matrix:
# cars.kmed.3 <- pam(dist.cars, k=3, diss=T)

cars.kmed.3$clustering  # printing the "clustering vector"

cars.kmed.3$silinfo$avg.width  #printing the average silhouette width

### A little function to calculate the average silhouette width
### for a variety of choices of k:

my.k.choices <- 2:8
avg.sil.width <- rep(0, times=length(my.k.choices))
for (ii in (1:length(my.k.choices)) ){
avg.sil.width[ii] <- pam(cars.std, k=my.k.choices[ii])$silinfo$avg.width
}
print( cbind(my.k.choices, avg.sil.width) )

# A LARGE average silhouette width indicates that the observations are properly clustered.

# Maybe k=2 is the best choice of k here?


cars.3.clust <- lapply(1:3, function(nc) row.names(cars.data)[cars.kmed.3$clustering==nc])  
cars.3.clust   # printing the clusters in terms of the car names


# Cluster 1 seems to be mostly compact cars, Cluster 2 is sports cars, Cluster 3 is large luxury sedans

############# Visualization of Clusters:

## Built-in plots available with the pam function:

# The "clusplot":

plot(cars.kmed.3, which.plots=1)

# The clusplot (in the "cluster" library) can actually be used with 
# any clustering partition by entering the data set and the clustering vector, e.g.:

clusplot(food[,-1], food.k4$cluster)

# The "silhouette plot":

plot(cars.kmed.3, which.plots=2)

# This shows which observations are "best clustered."


####################################################
####################################################
# 
# Choosing the number of clusters k using the average silhouette width criterion.
#
####################################################
####################################################

# When using pam, the output will give you the average silhouette width (see above code).

# We can also get the average silhouette width when using other algorithms:

### With a hierarchical method (Complete linkage here):

dist.food <- dist(food.std)
food.complete.link <- hclust(dist.food, method='complete')

summary(silhouette(cutree(food.complete.link, k=2), dist(food.std)))$avg.width
summary(silhouette(cutree(food.complete.link, k=3), dist(food.std)))$avg.width
summary(silhouette(cutree(food.complete.link, k=4), dist(food.std)))$avg.width
summary(silhouette(cutree(food.complete.link, k=5), dist(food.std)))$avg.width

### With k-means:

summary(silhouette(kmeans(food.std, centers=2, iter.max=100, nstart=25)$cluster, dist(food.std)))$avg.width
summary(silhouette(kmeans(food.std, centers=3, iter.max=100, nstart=25)$cluster, dist(food.std)))$avg.width
summary(silhouette(kmeans(food.std, centers=4, iter.max=100, nstart=25)$cluster, dist(food.std)))$avg.width
summary(silhouette(kmeans(food.std, centers=5, iter.max=100, nstart=25)$cluster, dist(food.std)))$avg.width

# In each case, we might choose the value of k associated with the LARGEST average silhouette width.

################################
################################
## 
##  Plotting the WSS for several choices of k
##
################################
################################

# This is a recommended method for choosing k in K-means clustering.

# For the cars data, let's consider letting k vary up to 5.

### CODE FOR WSS PLOT BEGINS HERE ###
##
#Enter name of the data matrix to be clustered here:
my.data.matrix <- cars.std  

my.k.choices <- 2:5
n <- length(my.data.matrix[,1])
wss1 <- (n-1)*sum(apply(my.data.matrix,2,var))
wss <- numeric(0)
for(i in my.k.choices) {
  W <- sum(kmeans(my.data.matrix,i)$withinss)
  wss <- c(wss,W)
}
wss <- c(wss1,wss)
plot(c(1,my.k.choices),wss,type='l',xlab='Number of clusters', ylab='Within-groups sum-of-squares', lwd=2)
##
### CODE FOR WSS PLOT ENDS HERE ###

# For what value of k does the elbow of the plot occur?