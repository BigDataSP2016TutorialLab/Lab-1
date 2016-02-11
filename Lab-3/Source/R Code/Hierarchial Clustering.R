food <- read.table("http://www.stat.sc.edu/~hitchcock/foodstuffs.txt", header=T)

attach(food)

std <- apply(food[,-1], 2, sd)
food.std <- sweep(food[,-1],2,std,FUN="/") 

dist.food <- dist(food.std)

food.single.link <- hclust(dist.food, method='single')

plclust(food.single.link, labels=Food, ylab="Distance")

windows()
food.complete.link <- hclust(dist.food, method='complete')

plclust(food.complete.link, labels=Food, ylab="Distance")

windows()

food.avg.link <- hclust(dist.food, method='average')

plclust(food.avg.link, labels=Food, ylab="Distance")

cut.2 <- cutree(food.complete.link, k=2)
cut.2 

food.2.clust <- lapply(1:2, function(nc) Food[cut.2==nc])  
food.2.clust

cut.5 <- cutree(food.complete.link, k=5)

cut.5 <- cutree(food.complete.link, h=3.5)  

cut.5

food.5.clust <- lapply(1:5, function(nc) Food[cut.5==nc])  
food.5.clust

pairs(food[,-1], panel=function(x,y) text(x,y,cut.5))

food.pc <- princomp(food[,-1],cor=T)

my.color.vector <- rep("green", times=nrow(food))
my.color.vector[cut.5==2] <- "blue"
my.color.vector[cut.5==3] <- "red"
my.color.vector[cut.5==4] <- "orange"
my.color.vector[cut.5==5] <- "brown"

par(pty="s")
plot(food.pc$scores[,1], food.pc$scores[,2], ylim=range(food.pc$scores[,1]), 
     xlab="PC 1", ylab="PC 2", type ='n', lwd=2)
text(food.pc$scores[,1], food.pc$scores[,2], labels=Food, cex=0.7, lwd=2,
     col=my.color.vector)