# -*- coding: utf-8 -*-
# Generated by Django 1.11.3 on 2017-08-05 14:11
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('polls', '0006_auto_20170805_1408'),
    ]

    operations = [
        migrations.AlterField(
            model_name='questionnaire',
            name='questionnaire_field',
            field=models.CharField(max_length=50),
        ),
    ]
