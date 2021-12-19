from django.db import models

# Create your models here.








class City(models.Model):
    cityName = models.CharField(max_length=50)

    def __str__(self):
        return self.cityName

class Path(models.Model):
    pathDescription = models.CharField(max_length=150)
    difficulty = models.IntegerField()

    def __str__(self):
        return self.pathDescription

class Question(models.Model):
    questionText = models.CharField(max_length=150)
    questionAnswer = models.CharField(max_length=100)
    points = models.IntegerField()
    questionHelpFirst = models.CharField(max_length=150)
    questionHelpSecond = models.CharField(max_length=150)
    qrCode = models.CharField(max_length=150)
    locationHint = models.CharField(max_length=150)
    locationName = models.CharField(max_length=100)
    locationLon = models.DecimalField(max_digits=15, decimal_places=7)
    locationLat = models.DecimalField(max_digits=15, decimal_places=7)
    city = models.ForeignKey(City, on_delete=models.CASCADE)

    #prevod na engleski
    questionTextEng = models.CharField(max_length=150, default="Question text")
    questionAnswerEng = models.CharField(max_length=100, default="Question answer")
    questionHelpFirstEng = models.CharField(max_length=150, default="Question first help")
    questionHelpSecondEng = models.CharField(max_length=150, default="Question second help")

    locationHintEng = models.CharField(max_length=150)
    locationNameEng = models.CharField(max_length=100)

    def __str__(self):
        return self.questionText

class PathQuestion(models.Model):
    path = models.ForeignKey(Path, on_delete=models.CASCADE)
    question = models.ForeignKey(Question, on_delete=models.CASCADE)

    def __str__(self):
        pathQuestion = 'Path: ' + str(self.path) + ', Question: ' + str(self.question)
        return pathQuestion


class User(models.Model):
    gmail = models.CharField(max_length=150)
    username = models.CharField(max_length=50)
    points = models.IntegerField()
    isRangListAllowed = models.BooleanField(default=False)
    currentPathQuestion = models.ForeignKey(PathQuestion, on_delete=models.CASCADE, null = True)
    firstHelpUsed = models.BooleanField(default=False)
    secondHelpUsed = models.BooleanField(default=False)
    #jezik moze biti en ili srp
    language = models.CharField(max_length=5)
    currentPathId = models.ForeignKey(Path, on_delete=models.CASCADE, null = True)
    

    def __str__(self):
        return self.username




class Error(models.Model):
    errorText = models.CharField(max_length=150)
    isSolved = models.BooleanField(default=False)

    def __str__(self):
        return self.errorText


 