# Generated by Django 3.2.3 on 2021-06-10 14:30

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('CodeHuntBL', '0005_auto_20210527_1331'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='language',
            field=models.CharField(default='srp', max_length=5),
            preserve_default=False,
        ),
    ]