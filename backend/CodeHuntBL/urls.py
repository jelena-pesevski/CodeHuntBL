from django.urls import path

from . import views

urlpatterns = [
	path('',views.home, name='home'),
    path('test/',views.home, name='home'),

    path('checkifuserisregistered/',views.checkIfUserIsRegistered, name='checkIfUserIsRegistered'),
    path('getquestionfromqrcode/',views.getQuestionFromQRCode, name='getQuestionFromQRCode'),
    path('checkanswer/',views.checkAnswer, name='checkAnswer'),
    path('addnewuser/',views.addNewUser, name='addNewUser'),
    path('getallquestionsfromselectedpath/',views.getAllQuestionsFromSelectedPath, name='getAllQuestionsFromSelectedPath'),
    path('getallpaths/',views.getAllPaths, name='getAllPaths'),
    path('getrankinglist/',views.getRankingList, name='getRankingList'),
    path('changeoptionshowingonrankinglist/',views.changeUserOptionToShowOnRankigList, name='changeUserOptionToShowOnRankigList'),
    path('updateuserpoint/',views.updateUserPoints, name='updateUserPoints'),
    path('getuserdata/',views.getUserData, name='getUserData'),
    path('getnextquestion/',views.getNextQuestion, name='getNextQuestion'),
    path('saveuserdata/',views.saveUserData, name='saveUserData'),
    path('geteuserlastquestion/',views.getUserLastQuestion, name='getUserLastQuestion'),
    path('saveuserusehelp/',views.saveUserUseHelp, name='saveUserUseHelp'),
    path('resetuserusedhelp/',views.reserUserUsedHelp, name='reserUserUsedHelp'),
    path('reporterror/',views.reportError, name='reporterror'),
    path('chengeusersettings/',views.changeUserSettings, name='chengeusersettings'),
    path('chengerankinglistoption/',views.changeUserSettingsShowOnRankingList, name='chengerankinglistoption'),
    path('chengeusername/',views.changeUserSettingsUsername, name='chengeusername'),
    path('chengeuserlanguage/',views.changeUserLanguage, name='chengeuserlanguage'),
    path('getallansweredquestionsfromselectedpath/',views.getAllQuestionsFromSelectedPathThatAreAnswered, name='getallansweredquestionsfromselectedpath'),
    path('getuserdataadditional/',views.getUserDataUsingId, name='getuserdataadditional'),
    path('saveuserendpath/',views.setUserToAnotherPath, name='saveuserendpath')




]