# Generated by Django 3.2.3 on 2021-06-10 14:44

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('CodeHuntBL', '0007_auto_20210610_1631'),
    ]

    operations = [
        migrations.AddField(
            model_name='question',
            name='locationHintEng',
            field=models.CharField(default='Location test', max_length=150),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='question',
            name='locationNameEng',
            field=models.CharField(default='Location hint', max_length=100),
            preserve_default=False,
        ),
    ]
