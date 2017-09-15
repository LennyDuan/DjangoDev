# -*- coding: utf-8 -*-
# Generated by Django 1.11.3 on 2017-09-15 08:40
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('polls', '0015_auto_20170915_0837'),
    ]

    operations = [
        migrations.AlterField(
            model_name='study',
            name='study_active',
            field=models.BooleanField(default=False, verbose_name='Active Study'),
        ),
        migrations.AlterField(
            model_name='study',
            name='study_end_date',
            field=models.DateTimeField(verbose_name='Study End Date end'),
        ),
        migrations.AlterField(
            model_name='study',
            name='study_start_date',
            field=models.DateTimeField(auto_now_add=True, verbose_name='Study Date Published'),
        ),
    ]