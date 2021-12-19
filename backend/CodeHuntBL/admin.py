from django.contrib import admin

from.models import City, Path, Question, PathQuestion, User, Error

# Register your models here.


admin.site.register(City)
admin.site.register(Path)
admin.site.register(Question)
admin.site.register(PathQuestion)
admin.site.register(User)
admin.site.register(Error)