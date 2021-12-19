from django.db.models.deletion import SET_NULL
from django.shortcuts import render
from django.http import HttpResponse

from django.http import JsonResponse
from django.forms.models import model_to_dict
import json

from .models import Error, User, Question, PathQuestion, Path

# Create your views here.

def home(request):
	return HttpResponse("Hello World")

'''
#127.0.0.1:8000/app/checkifuserisregistered/?gmail=user1@gmail.com
def checkIfUserIsRegistered(request):
	try:
		gmailGet = request.GET["gmail"]

		if User.objects.filter(gmail=gmailGet).exists():
			data = {
				'isRegistered': True
			}
		else:
			data = {
				'isRegistered': False
			}
		
		return JsonResponse(data)
	except Exception as e:
		data = {
			'isRegistered': False,
			'exceptionMessage': str(e)
		}
		return JsonResponse(data)
'''

#127.0.0.1:8000/app/checkifuserisregistered/?gmail=user1@gmail.com
def checkIfUserIsRegistered(request):
	try:
		gmailGet = request.GET["gmail"]

		userObjTemp = User.objects.get(gmail=gmailGet)

		if userObjTemp.currentPathQuestion is not None:
			pathIdSend = userObjTemp.currentPathQuestion.path.id
			questionIdSend = userObjTemp.currentPathQuestion.question.id
		else:
			pathIdSend = 1
			questionIdSend = -1


		if userObjTemp is not None:
			data = {
				'isRegistered': True,
				'gmail': userObjTemp.gmail,
				'userId': userObjTemp.id,
				'username': userObjTemp.username,
				'points': userObjTemp.points,
				'isRangListAllowed': userObjTemp.isRangListAllowed,
				'pathId': pathIdSend,
				'questionId': questionIdSend,
				'language': userObjTemp.language
			}

		
		return JsonResponse(data)
	except Exception as e:
		data = {
			'isRegistered': False,
			'gmail': None,
			'userId': None,
			'username': None,
			'points': None,
			'isRangListAllowed': None,
			'pathId': None,
			'questionId': None,
			'language': None,
			'exceptionMessage': str(e)
		}
		return JsonResponse(data)




'''
#127.0.0.1:8000/app/addnewuser/?gmail=userTEST2@gmail.com&username=userTest2&startpatquestionid=1
def addNewUser(request):
	try:
		gmailGet = request.GET["gmail"]
		usernameGet = request.GET["username"]
		startpatquestionidGet = request.GET["startpatquestionid"]

		pathquestionObj = PathQuestion.objects.get(id = startpatquestionidGet)

		#provjera da li postoji vec user da tim gmailom
		num_results = User.objects.filter(gmail = gmailGet).count()
		if num_results > 0:
			raise Exception("User with that gmail already exists")
		#create new user in database
		newUserObj = User(gmail = gmailGet, username = usernameGet, points = 0, isRangListAllowed = True, currentPathQuestion = pathquestionObj)

		newUserObj.save()

		data = {
			'addedNewUser': True,
			'userId': newUserObj.id
    	}
		return JsonResponse(data)
	except Exception as e:
		data = {
			'addedNewUser': False,
			'exceptionMessage': str(e)
    	}
		return JsonResponse(data)
'''


#127.0.0.1:8000/app/addnewuser/?gmail=userTEST2@gmail.com&username=userTest2&startpatquestionid=1
def addNewUserSTARAVERZIJA(request):
	try:
		gmailGet = request.GET["gmail"]
		usernameGet = request.GET["username"]
		startpatquestionidGet = request.GET["startpatquestionid"]

		pathquestionObj = PathQuestion.objects.get(id = startpatquestionidGet)
		
		#provjera da li postoji vec user da tim gmailom
		num_results = User.objects.filter(gmail = gmailGet).count()
		if num_results > 0:
			raise Exception("User with that gmail already exists")
		#create new user in database
		newUserObj = User(gmail = gmailGet, username = usernameGet, points = 0, isRangListAllowed = True, currentPathQuestion = None)

		newUserObj.save()

		data = {
			'addedNewUser': True,
			'gmail': newUserObj.gmail,
			'userId': newUserObj.id,
			'username': newUserObj.username,
			'points': newUserObj.points,
			'isRangListAllowed': newUserObj.isRangListAllowed,
			'pathId': 1,
			'questionId': None
			#'pathId': newUserObj.currentPathQuestion.path.id,
			#'questionId': newUserObj.currentPathQuestion.question.id
    	}
		return JsonResponse(data)
	except Exception as e:
		data = {
			'addedNewUser': False,
			'gmail': newUserObj.gmail,
			'userId': newUserObj.id,
			'username': newUserObj.username,
			'points': newUserObj.points,
			'isRangListAllowed': newUserObj.isRangListAllowed,
			'pathId': 1,
			'questionId': None,
			#'pathId': newUserObj.currentPathQuestion.path.id,
			#'questionId': newUserObj.currentPathQuestion.question.id,
			'exceptionMessage': str(e)
    	}
		return JsonResponse(data)



#127.0.0.1:8000/app/addnewuser/?gmail=userTEST2@gmail.com&username=userTest2&startpatquestionid=1&language=en
def addNewUser(request):
	try:
		gmailGet = request.GET["gmail"]
		usernameGet = request.GET["username"]
		startpatquestionidGet = request.GET["startpatquestionid"]

		languageGet = request.GET["language"]
		pathquestionObj = PathQuestion.objects.get(id = startpatquestionidGet)
		
		#provjera da li postoji vec user da tim gmailom
		num_results = User.objects.filter(gmail = gmailGet).count()
		if num_results > 0:
			raise Exception("User with that gmail already exists")

		#provjera da li postoji vec user da tim username-om
		num_results = User.objects.filter(username = usernameGet).count()
		if num_results > 0:
			raise Exception("User with that username already exists")

		#putanja sa id=1 je prva putanja
		pathObjTemp = Path.objects.get(id = 1)
		#create new user in database
		newUserObj = User(gmail = gmailGet, username = usernameGet, points = 0, isRangListAllowed = True, currentPathQuestion = None, language = languageGet,
		currentPathId = pathObjTemp)

		newUserObj.save()

		data = {
			'addedNewUser': True,
			'gmail': newUserObj.gmail,
			'userId': newUserObj.id,
			'username': newUserObj.username,
			'points': newUserObj.points,
			'isRangListAllowed': newUserObj.isRangListAllowed,
			'pathId': 1,
			'questionId': None
			#'pathId': newUserObj.currentPathQuestion.path.id,
			#'questionId': newUserObj.currentPathQuestion.question.id
    	}
		return JsonResponse(data)
	except Exception as e:
		data = {
			'addedNewUser': False,
			'gmail': newUserObj.gmail,
			'userId': newUserObj.id,
			'username': newUserObj.username,
			'points': newUserObj.points,
			'isRangListAllowed': newUserObj.isRangListAllowed,
			'pathId': 1,
			'questionId': None,
			#'pathId': newUserObj.currentPathQuestion.path.id,
			#'questionId': newUserObj.currentPathQuestion.question.id,
			'exceptionMessage': str(e)
    	}
		return JsonResponse(data)


#127.0.0.1:8000/app/saveuserdata/?userid=1&patthid=1&questionid=1
def saveUserData(request):
	try:
		userIdGet = request.GET["userid"]
		patthIdGet = request.GET["patthid"]
		questionIdGet = request.GET["questionid"]
		
		#prvo da dobijem na osnovu questionid i path id => PathQuestion id 
		#pa onda poslije toga sacuvam u user tabelu sa userom userid da se nalazi na PathQuestion id trenutno
		pathObjTemp = Path.objects.get(id = patthIdGet)

		if int(questionIdGet) != -1:
			questionObjGet = Question.objects.get(id = questionIdGet)
			pathquestionObj = PathQuestion.objects.get(path = pathObjTemp, question = questionObjGet)

			User.objects.filter(id = userIdGet).update(currentPathQuestion = pathquestionObj)


		data = {
			'successful': True
    	}
		return JsonResponse(data)
	except Exception as e:	
		data = {
			'successful': False,
			'exceptionMessage': str(e)
    	}
		return JsonResponse(data)


#127.0.0.1:8000/app/geteuserlastquestion/?userid=1
def getUserLastQuestion(request):
	try:
		userIdGet = request.GET["userid"]
		

		userObjTemp = User.objects.get(id = userIdGet)
		pathquestionObj = userObjTemp.currentPathQuestion

		if pathquestionObj is not None:

			pathObjTemp = pathquestionObj.path
			questionObjTemp = pathquestionObj.question

			if pathObjTemp.id is not None:
				data = {
					'successful': True,
					'pathid':pathObjTemp.id,
					'questionid':questionObjTemp.id
				}
			else:
				data = {
					'successful': True,
					'pathid':pathObjTemp.id,
					'questionid':-1
				}
		else:

			data = {
				'successful': True,
				'pathid':userObjTemp.currentPathId.id,
				'questionid':-1
			}

		return JsonResponse(data)
	except Exception as e:	
		data = {
			'successful': False,
			'exceptionMessage': str(e)
    	}
		return JsonResponse(data)



#127.0.0.1:8000/app/getallpaths/
def getAllPaths(request):
	try:
		pathObjects = Path.objects.filter()

		return_dict = {}
		allPaths = []
		for obj in pathObjects:
			data = {
				"id": obj.id,
				"pathDescription": obj.pathDescription,
				"difficulty": obj.difficulty
			}
			allPaths.append(data)
		
		return_dict["paths"] = allPaths
		return JsonResponse(return_dict)
	except Exception as ex:
		data = {
				"id": None,
				"pathDescription": None,
				"difficulty": None,
				"ExceptionMessage" : str(ex)
		}
		return JsonResponse(data)


#127.0.0.1:8000/app/getallquestionsfromselectedpath/?pathid=1
def getAllQuestionsFromSelectedPath(request):
	try:
		pathIdGet = request.GET["pathid"]

		pathquestionObjects = PathQuestion.objects.filter(path = pathIdGet)

		return_dict = {}
		allQuestionsFromPath = []
		for obj in pathquestionObjects:

			data = {
					'questionId': obj.question.id,
					'questionText': obj.question.questionText,
					'points': obj.question.points,
					'questionAnswer': obj.question.questionAnswer,
					'questionHelpFirst': obj.question.questionHelpFirst,
					'questionHelpSecond': obj.question.questionHelpSecond,
					'qrCode': obj.question.qrCode,
					'locationHint': obj.question.locationHint,
					'locationName': obj.question.locationName,
					'locationLon': str(obj.question.locationLon),
					'locationLat': str(obj.question.locationLat),
					'city': obj.question.city.cityName
			}
			allQuestionsFromPath.append(data)

		return_dict["questions"] = allQuestionsFromPath
		return JsonResponse(return_dict)
	except Exception as e:
		data = {
				'questionId': None,
				'questionText': None,
				'points': None,
				'questionAnswer': None,
				'questionHelpFirst': None,
				'questionHelpSecond': None,
				'qrCode': None,
				'locationHint': None,
				'locationName': None,
				'locationLon': None,
				'locationLat': None,
				'city': None
		}

		return_dict["questions"] = allQuestionsFromPath
		return JsonResponse(return_dict)

	
#127.0.0.1:8000/app/getallansweredquestionsfromselectedpath/?pathid=1&lastansweresquestionid=5&userid=1
def getAllQuestionsFromSelectedPathThatAreAnswered(request):
	try:
		pathIdGet = request.GET["pathid"]
		currentQuestionIdGet = request.GET["lastansweresquestionid"]
		userIdGet = request.GET["userid"]

		pathquestionObjects = PathQuestion.objects.filter(path = pathIdGet)

		userObj = User.objects.get(id = userIdGet)
		return_dict = {}
		allQuestionsFromPath = []
		for obj in pathquestionObjects:
			if obj.question.id <= int(currentQuestionIdGet):
				
				if userObj.language == "en":
					data = {
							'questionId': obj.question.id,
							'questionText': obj.question.questionTextEng,
							'points': obj.question.points,
							'questionAnswer': obj.question.questionAnswerEng,
							'questionHelpFirst': obj.question.questionHelpFirstEng,
							'questionHelpSecond': obj.question.questionHelpSecondEng,
							'qrCode': obj.question.qrCode,
							'locationHint': obj.question.locationHintEng,
							'locationName': obj.question.locationNameEng,
							'locationLon': str(obj.question.locationLon),
							'locationLat': str(obj.question.locationLat),
							'city': obj.question.city.cityName
					}
				else:
					data = {
							'questionId': obj.question.id,
							'questionText': obj.question.questionText,
							'points': obj.question.points,
							'questionAnswer': obj.question.questionAnswer,
							'questionHelpFirst': obj.question.questionHelpFirst,
							'questionHelpSecond': obj.question.questionHelpSecond,
							'qrCode': obj.question.qrCode,
							'locationHint': obj.question.locationHint,
							'locationName': obj.question.locationName,
							'locationLon': str(obj.question.locationLon),
							'locationLat': str(obj.question.locationLat),
							'city': obj.question.city.cityName
					}


				allQuestionsFromPath.append(data)

		return_dict["questions"] = allQuestionsFromPath
		return JsonResponse(return_dict)
	except Exception as e:
		data = {
				'questionId': None,
				'questionText': None,
				'points': None,
				'questionAnswer': None,
				'questionHelpFirst': None,
				'questionHelpSecond': None,
				'qrCode': None,
				'locationHint': None,
				'locationName': None,
				'locationLon': None,
				'locationLat': None,
				'city': None
		}

		return_dict["questions"] = allQuestionsFromPath
		return JsonResponse(return_dict)

#127.0.0.1:8000/app/getquestionfromqrcode/?qrcode=aaaaa
def getQuestionFromQRCode(request):
	try:
		QRCodeGet = request.GET["qrcode"]

		questionFromDBObj = Question.objects.get(qrCode=QRCodeGet)

		if questionFromDBObj is not None:
			data = {
				'questionId': questionFromDBObj.id,
				'questionText': questionFromDBObj.questionText,
				'points': questionFromDBObj.points,
				'questionAnswer': questionFromDBObj.questionAnswer,
				'questionHelpFirst': questionFromDBObj.questionHelpFirst,
				'questionHelpSecond': questionFromDBObj.questionHelpSecond,
				'qrCode': questionFromDBObj.qrCode,
				'locationHint': questionFromDBObj.locationHint,
				'locationName': questionFromDBObj.locationName,
				'locationLon': str(questionFromDBObj.locationLon),
				'locationLat': str(questionFromDBObj.locationLat),
				'city': questionFromDBObj.city.cityName
			}

		#dict_obj = model_to_dict( questionFromDBObj )
		#serialized = json.dumps(dict_obj)

		return JsonResponse(data)
	except Exception as e:
		data = {
				'questionId': None,
				'questionText': None,
				'points': None,
				'questionAnswer': None,
				'questionHelpFirst': None,
				'questionHelpSecond': None,
				'qrCode': None,
				'locationHint': None,
				'locationName': None,
				'locationLon': None,
				'locationLat': None,
				'city': None
			}

		return JsonResponse(data)



'''
#127.0.0.1:8000/app/getnextquestion/?pathid=1&previousquestionid=1
def getNextQuestion(request):
	try:
		pathIdGet = request.GET["pathid"]
		previousQuestionIdGet = request.GET["previousquestionid"]

		#uzmem sva pitanja koja pripadaju toj putanja i tada trazim pitanje koje ima veci id od prethodnog pitanja koje je poslato
		#ako ga nema vratum null, to je kraj te putanje
		pathObject = Path.objects.get(id = pathIdGet)
		pathQuestionObjectTemp = PathQuestion.objects.filter(path = pathObject)

		requiredId = -1
		for obj in pathQuestionObjectTemp:
			currId = obj.question.id
			if int(currId) > int(previousQuestionIdGet):
				requiredId = int(currId)
				break
		
		if requiredId > -1:
			questionFromDBObj = PathQuestion.objects.get(path=pathObject, question=requiredId)

			if questionFromDBObj is not None:
				dataTemp = {
					'successful':True,
					'questionId': questionFromDBObj.question.id,
					'questionText': questionFromDBObj.question.questionText,
					'points': questionFromDBObj.question.points,
					'questionAnswer': questionFromDBObj.question.questionAnswer,
					'questionHelpFirst': questionFromDBObj.question.questionHelpFirst,
					'questionHelpSecond': questionFromDBObj.question.questionHelpSecond,
					'qrCode': questionFromDBObj.question.qrCode,
					'locationHint': questionFromDBObj.question.locationHint,
					'locationName': questionFromDBObj.question.locationName,
					'locationLon': str(questionFromDBObj.question.locationLon),
					'locationLat': str(questionFromDBObj.question.locationLat),
					'city': questionFromDBObj.question.city.cityName
				}
			else:
				dataTemp = {
					'successful':False,
					'exceptionMessage':'End of path (there is no more questions).',
					'questionId': None,
					'questionText': None,
					'points': None,
					'questionAnswer': None,
					'questionHelpFirst': None,
					'questionHelpSecond': None,
					'qrCode': None,
					'locationHint': None,
					'locationName': None,
					'locationLon': None,
					'locationLat': None,
					'city': None
				}

			return JsonResponse(dataTemp)
		else:
			dataTemp = {
					'successful':False,
					'exceptionMessage':'End of path (there is no more questions).',
					'questionId': None,
					'questionText': None,
					'points': None,
					'questionAnswer': None,
					'questionHelpFirst': None,
					'questionHelpSecond': None,
					'qrCode': None,
					'locationHint': None,
					'locationName': None,
					'locationLon': None,
					'locationLat': None,
					'city': None
				}

			return JsonResponse(dataTemp)


		#dict_obj = model_to_dict( questionFromDBObj )
		#serialized = json.dumps(dict_obj)

		#return JsonResponse(dataTemp)
	except Exception as e:
		data = {
				'successful':False,
				'exceptionMessage':str(e),
				'questionId': None,
				'questionText': None,
				'points': None,
				'questionAnswer': None,
				'questionHelpFirst': None,
				'questionHelpSecond': None,
				'qrCode': None,
				'locationHint': None,
				'locationName': None,
				'locationLon': None,
				'locationLat': None,
				'city': None
			}

		return JsonResponse(data)
'''



#127.0.0.1:8000/app/getnextquestion/?pathid=1&previousquestionid=1&userid=1
def getNextQuestion(request):
	try:
		pathIdGet = request.GET["pathid"]
		previousQuestionIdGet = request.GET["previousquestionid"]
		userIdGet = request.GET["userid"]

		userObj = User.objects.get(id = userIdGet)

		#uzmem sva pitanja koja pripadaju toj putanja i tada trazim pitanje koje ima veci id od prethodnog pitanja koje je poslato
		#ako ga nema vratum null, to je kraj te putanje
		pathObject = Path.objects.get(id = pathIdGet)
		pathQuestionObjectTemp = PathQuestion.objects.filter(path = pathObject)

		requiredId = -1
		for obj in pathQuestionObjectTemp:
			currId = obj.question.id
			if int(currId) > int(previousQuestionIdGet):
				requiredId = int(currId)
				break
		
		if requiredId > -1:
			questionFromDBObj = PathQuestion.objects.get(path=pathObject, question=requiredId)

			if questionFromDBObj is not None:
				if userObj.language == 'srp':
					dataTemp = {
						'successful':True,
						'questionId': questionFromDBObj.question.id,
						'questionText': questionFromDBObj.question.questionText,
						'points': questionFromDBObj.question.points,
						'questionAnswer': questionFromDBObj.question.questionAnswer,
						'questionHelpFirst': questionFromDBObj.question.questionHelpFirst,
						'questionHelpSecond': questionFromDBObj.question.questionHelpSecond,
						'questionHelpFirstUsed': userObj.firstHelpUsed,
						'questionHelpSecondUsed': userObj.secondHelpUsed,
						'qrCode': questionFromDBObj.question.qrCode,
						'locationHint': questionFromDBObj.question.locationHint,
						'locationName': questionFromDBObj.question.locationName,
						'locationLon': str(questionFromDBObj.question.locationLon),
						'locationLat': str(questionFromDBObj.question.locationLat),
						'city': questionFromDBObj.question.city.cityName
					}
				else:
					dataTemp = {
						'successful':True,
						'questionId': questionFromDBObj.question.id,
						'questionText': questionFromDBObj.question.questionTextEng,
						'points': questionFromDBObj.question.points,
						'questionAnswer': questionFromDBObj.question.questionAnswerEng,
						'questionHelpFirst': questionFromDBObj.question.questionHelpFirstEng,
						'questionHelpSecond': questionFromDBObj.question.questionHelpSecondEng,
						'questionHelpFirstUsed': userObj.firstHelpUsed,
						'questionHelpSecondUsed': userObj.secondHelpUsed,
						'qrCode': questionFromDBObj.question.qrCode,
						'locationHint': questionFromDBObj.question.locationHintEng,
						'locationName': questionFromDBObj.question.locationNameEng,
						'locationLon': str(questionFromDBObj.question.locationLon),
						'locationLat': str(questionFromDBObj.question.locationLat),
						'city': questionFromDBObj.question.city.cityName
					}
				
			else:
				dataTemp = {
					'successful':False,
					'exceptionMessage':'End of path (there is no more questions).',
					'questionId': None,
					'questionText': None,
					'points': None,
					'questionAnswer': None,
					'questionHelpFirst': None,
					'questionHelpSecond': None,
					'questionHelpFirstUsed': None,
					'questionHelpSecondUsed': None,
					'qrCode': None,
					'locationHint': None,
					'locationName': None,
					'locationLon': None,
					'locationLat': None,
					'city': None
				}

			return JsonResponse(dataTemp)
		else:
			dataTemp = {
					'successful':False,
					'exceptionMessage':'End of path (there is no more questions).',
					'questionId': None,
					'questionText': None,
					'points': None,
					'questionAnswer': None,
					'questionHelpFirst': None,
					'questionHelpSecond': None,
					'questionHelpFirstUsed': None,
					'questionHelpSecondUsed': None,
					'qrCode': None,
					'locationHint': None,
					'locationName': None,
					'locationLon': None,
					'locationLat': None,
					'city': None
				}

			return JsonResponse(dataTemp)


		#dict_obj = model_to_dict( questionFromDBObj )
		#serialized = json.dumps(dict_obj)

		#return JsonResponse(dataTemp)
	except Exception as e:
		data = {
				'successful':False,
				'exceptionMessage':str(e),
				'questionId': None,
				'questionText': None,
				'points': None,
				'questionAnswer': None,
				'questionHelpFirst': None,
				'questionHelpSecond': None,
				'questionHelpFirstUsed': None,
				'questionHelpSecondUsed': None,
				'qrCode': None,
				'locationHint': None,
				'locationName': None,
				'locationLon': None,
				'locationLat': None,
				'city': None
			}

		return JsonResponse(data) 



#korisnik je odgovorio na sva pitanja iz ove putanja i sad ga postavi na sledecu ako ima putanja jos
#127.0.0.1:8000/app/saveuserendpath/?userid=1&pathid=1
def setUserToAnotherPath(request):
	try:
		pathIdGet = request.GET["pathid"]
		userIdGet = request.GET["userid"]

		userObj = User.objects.get(id = userIdGet)

		#snimi da je na novoj putanji
		pathObjTemp = Path.objects.get(id = (int(pathIdGet)+1))
		#User.objects.filter(id = userIdGet).update(username = "ninaaa")

		


		#stavi da je null trenutno pathquestion
		User.objects.filter(id = userIdGet).update(currentPathQuestion=None)

		#uzmem sva pitanja koja pripadaju toj putanja i tada trazim pitanje koje ima veci id od prethodnog pitanja koje je poslato
		#ako ga nema vratum null, to je kraj te putanje
		pathObject = Path.objects.get(id = int(pathIdGet))

		allPaths = Path.objects.all()

		nextPathId = -1
		for tempPath in allPaths:
			if tempPath.id > int(pathIdGet):
				nextPathId = tempPath.id
				break
		
		if nextPathId > -1:
			#ako ima putanja, provjeri da li ima pitanja za tu putanju
			nextPathObj = Path.objects.get(id = nextPathId)

			#dobij sva pitanja za tu putanju pa izvuci prvo pitanje
			allQuestForNextPath = PathQuestion.objects.filter(path = nextPathObj)

			#UPDATE ZA SLEDECU PUTANJU
			User.objects.filter(id = userIdGet).update(currentPathId = pathObjTemp)
			
			#firstPathQuestObjForNextPath = allQuestForNextPath[0]


			#if firstPathQuestObjForNextPath is not None:
			#		User.objects.filter(id = userIdGet).update(currentPathQuestion = firstPathQuestObjForNextPath)
			#else:
			#	raise Exception("There is no questions for that path!")
		else:
			raise Exception("There is no more paths!")

		dataTemp = {
			'successful':True,
			'aaa': pathObjTemp.id
		}

		return JsonResponse(dataTemp)
		

	except Exception as e:
		dataTemp = {
			'successful':False,
			'exceptionMessage': str(e)	
		}

		return JsonResponse(dataTemp)



'''
#127.0.0.1:8000/app/getnextquestion/?pathid=1&previousquestionid=1&userid=1
def getNextQuestion(request):
	try:
		pathIdGet = request.GET["pathid"]
		previousQuestionIdGet = request.GET["previousquestionid"]
		userIdGet = request.GET["userid"]

		userObj = User.objects.get(id = userIdGet)

		#uzmem sva pitanja koja pripadaju toj putanja i tada trazim pitanje koje ima veci id od prethodnog pitanja koje je poslato
		#ako ga nema vratum null, to je kraj te putanje
		pathObject = Path.objects.get(id = pathIdGet)
		pathQuestionObjectTemp = PathQuestion.objects.filter(path = pathObject)

		requiredId = -1
		for obj in pathQuestionObjectTemp:
			currId = obj.question.id
			if int(currId) > int(previousQuestionIdGet):
				requiredId = int(currId)
				break
		
		if requiredId > -1:
			questionFromDBObj = PathQuestion.objects.get(path=pathObject, question=requiredId)

			if questionFromDBObj is not None:
				dataTemp = {
					'successful':True,
					'questionId': questionFromDBObj.question.id,
					'questionText': questionFromDBObj.question.questionText,
					'points': questionFromDBObj.question.points,
					'questionAnswer': questionFromDBObj.question.questionAnswer,
					'questionHelpFirst': questionFromDBObj.question.questionHelpFirst,
					'questionHelpSecond': questionFromDBObj.question.questionHelpSecond,
					'questionHelpFirstUsed': userObj.firstHelpUsed,
					'questionHelpSecondUsed': userObj.secondHelpUsed,
					'qrCode': questionFromDBObj.question.qrCode,
					'locationHint': questionFromDBObj.question.locationHint,
					'locationName': questionFromDBObj.question.locationName,
					'locationLon': str(questionFromDBObj.question.locationLon),
					'locationLat': str(questionFromDBObj.question.locationLat),
					'city': questionFromDBObj.question.city.cityName
				}
			else:
				dataTemp = {
					'successful':False,
					'exceptionMessage':'End of path (there is no more questions).',
					'questionId': None,
					'questionText': None,
					'points': None,
					'questionAnswer': None,
					'questionHelpFirst': None,
					'questionHelpSecond': None,
					'questionHelpFirstUsed': None,
					'questionHelpSecondUsed': None,
					'qrCode': None,
					'locationHint': None,
					'locationName': None,
					'locationLon': None,
					'locationLat': None,
					'city': None
				}

			return JsonResponse(dataTemp)
		else:
			dataTemp = {
					'successful':False,
					'exceptionMessage':'End of path (there is no more questions).',
					'questionId': None,
					'questionText': None,
					'points': None,
					'questionAnswer': None,
					'questionHelpFirst': None,
					'questionHelpSecond': None,
					'questionHelpFirstUsed': None,
					'questionHelpSecondUsed': None,
					'qrCode': None,
					'locationHint': None,
					'locationName': None,
					'locationLon': None,
					'locationLat': None,
					'city': None
				}

			return JsonResponse(dataTemp)


		#dict_obj = model_to_dict( questionFromDBObj )
		#serialized = json.dumps(dict_obj)

		#return JsonResponse(dataTemp)
	except Exception as e:
		data = {
				'successful':False,
				'exceptionMessage':str(e),
				'questionId': None,
				'questionText': None,
				'points': None,
				'questionAnswer': None,
				'questionHelpFirst': None,
				'questionHelpSecond': None,
				'questionHelpFirstUsed': None,
				'questionHelpSecondUsed': None,
				'qrCode': None,
				'locationHint': None,
				'locationName': None,
				'locationLon': None,
				'locationLat': None,
				'city': None
			}

		return JsonResponse(data)

'''

#127.0.0.1:8000/app/checkanswer/?questionId=1&answer=Odgovor 1
def checkAnswer(request):
	try:
		questionIdGet = request.GET["questionId"]
		answerGet = request.GET["answer"]

		if Question.objects.filter(id = questionIdGet, questionAnswer = answerGet).exists():
			data = {
				'isCorrect': True
			}
		else:
			data = {
				'isCorrect': False
			}

		return JsonResponse(data)
	except Exception as e:
		data = {
			'isCorrect': False
		}

		return JsonResponse(data)


#127.0.0.1:8000/app/saveuserusehelp/?userid=1&helpid=1 (moze biti 1 ili 2)
def saveUserUseHelp(request):
	try:
		userIdGet = request.GET["userid"]
		helpIdGet = request.GET["helpid"]


		if int(helpIdGet) == 1:
			User.objects.filter(id = userIdGet).update(firstHelpUsed = True)
		elif int(helpIdGet) == 2:
			User.objects.filter(id = userIdGet).update(secondHelpUsed = True)
		else:
			data = {
				'isCorrect': False
			}
		
		data = {
				'isCorrect': True
			}
	
		return JsonResponse(data)
	except Exception as e:
		data = {
			'isCorrect': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)


#127.0.0.1:8000/app/resetuserusedhelp/?userid=1
def reserUserUsedHelp(request):
	try:
		userIdGet = request.GET["userid"]


		User.objects.filter(id = userIdGet).update(firstHelpUsed = False, secondHelpUsed = False)
		
		
		data = {
			'isCorrect': True
		}
	
		return JsonResponse(data)
	except Exception as e:
		data = {
			'isCorrect': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)



#za rang listu, uzmem sve korisnike i sortiram po broju bodova 
#127.0.0.1:8000/app/getrankinglist/
def getRankingList(request):
	try:
		usersObjects = User.objects.order_by('-points')[:30]

		return_dict = {}
		rankingList = []
		for obj in usersObjects:
			if obj.isRangListAllowed == True:
				data = {
						'id': obj.id,
						'gmail': obj.gmail,
						'username': obj.username,
						'points': obj.points,
				}
				rankingList.append(data)

		return_dict["rankingList"] = rankingList
		return JsonResponse(return_dict)
	except Exception as e:
		data = {
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)


#127.0.0.1:8000/app/changeoptionshowingonrankinglist/?userid=1&option=true
def changeUserOptionToShowOnRankigList(request):
	try:
		useridGet = request.GET["userid"]
		optionGet = request.GET["option"]

		if optionGet == 'true':
			User.objects.filter(id = useridGet).update(isRangListAllowed = True)
		elif optionGet == 'false':
			User.objects.filter(id = useridGet).update(isRangListAllowed = False)
		data = {
			'successful': True
		}


		return JsonResponse(data)
	except Exception as e:
		data = {
			'successful': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)


#127.0.0.1:8000/app/updateuserpoint/?userid=1&points=100
def updateUserPoints(request):
	try:
		useridGet = request.GET["userid"]
		pointsGet = request.GET["points"]

		User.objects.filter(id = useridGet).update(points = int(pointsGet))

		data = {
			'updated': True
		}

		return JsonResponse(data)
	except Exception as e:
		data = {
			'updated': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)




#nakon prijave get user data na osnovu email-a
#127.0.0.1:8000/app/getuserdata/?gmail=user1@gmail.com
def getUserData(request):
	try:
		gmailGet = request.GET["gmail"]

		userObj = User.objects.filter(gmail = gmailGet).get()

		data = {
			'id': userObj.id,
			'gmail': userObj.gmail,
			'username': userObj.username,
			'points': userObj.points,
			'isRangListAllowed': userObj.isRangListAllowed
		}

		return JsonResponse(data)
	except Exception as e:
		data = {
			'updated': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)



#127.0.0.1:8000/app/getuserdataadditional/?userid=1
def getUserDataUsingId(request):
	try:
		userIdGet = request.GET["userid"]

		userObj = User.objects.filter(id = userIdGet).get()

		data = {
			'id': userObj.id,
			'gmail': userObj.gmail,
			'username': userObj.username,
			'points': userObj.points,
			'isRangListAllowed': userObj.isRangListAllowed,
			'language': userObj.language
		}

		return JsonResponse(data)
	except Exception as e:
		data = {
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)





#127.0.0.1:8000/app/reporterror/?error=Neki tekst error message
def reportError(request):
	try:
		errorGet = request.GET["error"]

		newErrorObj = Error(errorText = errorGet, isSolved = False)
		newErrorObj.save()

		data = {
			'errorMessage': errorGet,
			'successful': True
		}

		return JsonResponse(data)
	except Exception as e:
		data = {
			'errorMessage': errorGet,
			'successful': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)
	

#jezik moze biti eng ili sr
#127.0.0.1:8000/app/chengeusersettings/?userid=1&newusername=newtestusername&setonrankinglist=false&newlanguage=srp
def changeUserSettings(request):
	try:
		userIdGet = request.GET["userid"]
		newUsernameGet = request.GET["newusername"]
		setOnRankinglistGet = request.GET["setonrankinglist"]
		newLanguageGet = request.GET["newlanguage"]

		if newLanguageGet != 'srp' and newLanguageGet != 'en':
			raise Exception("Unavailable language, set srp or en")
		isOnRangList = False
		if setOnRankinglistGet == 'true':
			isOnRangList = True
		User.objects.filter(id = userIdGet).update(username = newUsernameGet, isRangListAllowed = isOnRangList, language = newLanguageGet)


		data = {
			'successful': True
		}

		return JsonResponse(data)
	except Exception as e:
		data = {
			'successful': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)





#127.0.0.1:8000/app/chengeusername/?userid=1&newusername=newtestusername
def changeUserSettingsUsername(request):
	try:
		userIdGet = request.GET["userid"]
		newUsernameGet = request.GET["newusername"]

		#provjera da li postoji vec user da tim username-om
		#num_results = User.objects.filter(username = newUsernameGet).count()
		#userTempObject = User.objects.filter(username = newUsernameGet)

		tempUser = User.objects.get(id = userIdGet)

		if tempUser is not None and tempUser.id != int(userIdGet):
			#tempUser = User.objects.get(id = userIdGet)
			if tempUser.language == 'srp':
				raise Exception("Korisnik sa tim korisnickim imenom vec postoji!")
			else:	
				raise Exception("User with that username already exists")
			

		User.objects.filter(id = userIdGet).update(username = newUsernameGet)


		data = {
			'successful': True
		}

		return JsonResponse(data)
	except Exception as e:
		data = {
			'successful': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)



#127.0.0.1:8000/app/chengerankinglistoption/?userid=1&setonrankinglist=false
def changeUserSettingsShowOnRankingList(request):
	try:
		userIdGet = request.GET["userid"]
		setOnRankinglistGet = request.GET["setonrankinglist"]


		isOnRangList = False
		if setOnRankinglistGet == 'true':
			isOnRangList = True
		User.objects.filter(id = userIdGet).update(isRangListAllowed = isOnRangList)


		data = {
			'successful': True
		}

		return JsonResponse(data)
	except Exception as e:
		data = {
			'successful': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)



#jezik moze biti eng ili sr
#127.0.0.1:8000/app/chengeuserlanguage/?userid=1&newlanguage=srp
def changeUserLanguage(request):
	try:
		userIdGet = request.GET["userid"]
		newLanguageGet = request.GET["newlanguage"]

		if newLanguageGet != 'srp' and newLanguageGet != 'en':
			raise Exception("Unavailable language, set srp or en")

		User.objects.filter(id = userIdGet).update(language = newLanguageGet)


		data = {
			'successful': True
		}

		return JsonResponse(data)
	except Exception as e:
		data = {
			'successful': False,
			'exceptionMessage': str(e)
		}

		return JsonResponse(data)

