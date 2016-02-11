food <- read.table("http://www.stat.sc.edu/~hitchcock/foodstuffs.txt", header=T)

attach(food)

# The hclust function requires that a distance object be input:

# Let's first scale the data by dividing each variable by its standard deviation:

std <- apply(food[,-1], 2, sd) # finding standard deviations of variables
food.std <- sweep(food[,-1],2,std,FUN="/") 

# Calculating pairwise Euclidean distances between the (standardized) objects:

dist.food <- dist(food.std)

# Single linkage:

food.single.link <- hclust(dist.food, method='single')

# Plotting the single linkage dendrogram:

plclust(food.single.link, labels=Food, ylab="Distance")

windows() # opening new window while keeping previous one open

# complete linkage:

food.complete.link <- hclust(dist.food, method='complete')

# Plotting the complete linkage dendrogram:

plclust(food.complete.link, labels=Food, ylab="Distance")

windows() # opening new window while keeping previous one open

# Average linkage:

food.avg.link <- hclust(dist.food, method='average')

# Plotting the average linkage dendrogram:

plclust(food.avg.link, labels=Food, ylab="Distance")

# Note the complete linkage algorithm is slightly less prone to forming 
# "outlier-only" clusters here.

# Cutting the complete-linkage dendrogram to form k=2 clusters here:

cut.2 <- cutree(food.complete.link, k=2)
cut.2     # printing the "clustering vector"

food.2.clust <- lapply(1:2, function(nc) Food[cut.2==nc])  
food.2.clust   # printing the clusters in terms of the Food labels

# Suppose we preferred a 5-cluster solution:

cut.5 <- cutree(food.complete.link, k=5)

# Equivalently, in this case:
cut.5 <- cutree(food.complete.link, h=3.5)  
# h specifies the height at which the dendrogram should be cut

cut.5   # printing the "clustering vector"

food.5.clust <- lapply(1:5, function(nc) Food[cut.5==nc])  
food.5.clust   # printing the clusters in terms of the Food labels


############# Visualization of Clusters:

### Via the scatterplot matrix:

pairs(food[,-1], panel=function(x,y) text(x,y,cut.5))

# Cluster 1 seems to be the high-fat, high-energy foods (beef, ham, pork)
# Cluster 2 foods seem to have low iron (more white meats than red meats)
# Cluster 4 foods have low protein (the clams)
# Cluster 5 is a high-calcium outlier (canned sardines)

### Via a plot of the scores on the first 2 principal components, 
### with the clusters separated by color:

food.pc <- princomp(food[,-1],cor=T)

# Setting up the colors for the 5 clusters on the plot:
my.color.vector <- rep("green", times=nrow(food))
my.color.vector[cut.5==2] <- "blue"
my.color.vector[cut.5==3] <- "red"
my.color.vector[cut.5==4] <- "orange"
my.color.vector[cut.5==5] <- "brown"

# Plotting the PC scores:

par(pty="s")
plot(food.pc$scores[,1], food.pc$scores[,2], ylim=range(food.pc$scores[,1]), 
     xlab="PC 1", ylab="PC 2", type ='n', lwd=2)
text(food.pc$scores[,1], food.pc$scores[,2], labels=Food, cex=0.7, lwd=2,
     col=my.color.vector)

# What would this plot look like for the 2-cluster solution?
# For the 3-cluster solution?