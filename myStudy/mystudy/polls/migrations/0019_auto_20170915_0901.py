# -*- coding: utf-8 -*-
# Generated by Django 1.11.3 on 2017-09-15 09:01
from __future__ import unicode_literals

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('polls', '0018_auto_20170915_0848'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='userinfo',
            name='userInfo_email',
        ),
        migrations.RemoveField(
            model_name='userinfo',
            name='userInfo_name',
        ),
        migrations.AddField(
            model_name='userinfo',
            name='UserInfo_gender',
            field=models.CharField(default='Male', max_length=50),
        ),
        migrations.AddField(
            model_name='userinfo',
            name='userInfo_age',
            field=models.CharField(default='10', max_length=50),
        ),
        migrations.AlterField(
            model_name='feedback',
            name='feedback_end_date',
            field=models.DateField(auto_now=True, verbose_name='date published'),
        ),
        migrations.AlterField(
            model_name='feedback',
            name='feedback_start_date',
            field=models.DateField(auto_now_add=True, verbose_name='date published'),
        ),
        migrations.AlterField(
            model_name='study',
            name='study_end_date',
            field=models.DateField(verbose_name='Study End Date end'),
        ),
        migrations.AlterField(
            model_name='study',
            name='study_start_date',
            field=models.DateField(default=datetime.datetime.now, verbose_name='Study Date Published'),
        ),
        migrations.AlterField(
            model_name='userinfo',
            name='userInfo_end_date',
            field=models.DateField(verbose_name='date finished'),
        ),
        migrations.AlterField(
            model_name='userinfo',
            name='userInfo_start_date',
            field=models.DateField(verbose_name='date published'),
        ),
    ]
