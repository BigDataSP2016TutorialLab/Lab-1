install.packages("EMCluster")


set.seed(101)

slope1 <- -.3; intercept1 <- 1.5    # generating data from C_1
xs1 <- sample(seq(-2,2,len=201), 40)
ys1 <- intercept1 + slope1*xs1 + rnorm(length(xs1),0,.15) # add some noise

slope2 <- 1.2; intercept2 <- -.4    # generating data from C_2
xs2 <- sample(seq(-2,2,len=201), 40)
ys2 <- intercept2 + slope2*xs2 + rnorm(length(xs1),0,.15)

mydata <- rbind( cbind(xs1,ys1), cbind(xs2,ys2) ) 
plot(mydata, pch=19, xlab="X", ylab="Y")