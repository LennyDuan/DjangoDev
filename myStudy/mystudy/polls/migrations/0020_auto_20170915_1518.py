# -*- coding: utf-8 -*-
# Generated by Django 1.11.3 on 2017-09-15 15:18
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('polls', '0019_auto_20170915_0901'),
    ]

    operations = [
        migrations.RenameField(
            model_name='userinfo',
            old_name='UserInfo_gender',
            new_name='userInfo_gender',
        ),
    ]
