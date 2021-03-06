# Generated by Django 3.2.3 on 2021-06-10 14:31

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('CodeHuntBL', '0006_user_language'),
    ]

    operations = [
        migrations.AddField(
            model_name='question',
            name='questionAnswerEng',
            field=models.CharField(default='Question answer', max_length=100),
        ),
        migrations.AddField(
            model_name='question',
            name='questionHelpFirstEng',
            field=models.CharField(default='Question first help', max_length=150),
        ),
        migrations.AddField(
            model_name='question',
            name='questionHelpSecondEng',
            field=models.CharField(default='Question second help', max_length=150),
        ),
        migrations.AddField(
            model_name='question',
            name='questionTextEng',
            field=models.CharField(default='Question text', max_length=150),
        ),
    ]
