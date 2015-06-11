<?xml version="1.0" encoding="WINDOWS-1251" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:wp="www.planes.com">
    <xsl:template match="/">

        <table border="1" align="center">
            <p align="center">Военные самолеты</p>
            <tr bgcolor="#bce3e0">
                <td align="center" rowspan="3">
                    <strong>Модель</strong>
                </td>
                <td align="center" rowspan="3">
                    <strong>Происхождение</strong>
                </td>
                <td align="center" colspan="4">
                    <strong>Характеристики</strong>
                </td>
                <td align="center" colspan="3">
                    <strong>Параметры</strong>
                </td>
                <td align="center" rowspan="3">
                    <strong>Цена</strong>
                </td>
            </tr>
            <tr bgcolor="#bce3e0">
                <td align="center" rowspan="2">
                    <strong>Тип</strong>
                </td>
                <td align="center" rowspan="2">
                    <strong>Места</strong>
                </td>
                <td align="center" colspan="2">
                    <strong>Боекомплект</strong>
                </td>
                <td align="center" rowspan="2">
                    <strong>Длина</strong>
                </td>
                <td align="center" rowspan="2">
                    <strong>Ширина</strong>
                </td>
                <td align="center" rowspan="2">
                    <strong>Высота</strong>
                </td>
            </tr>
            <tr bgcolor="#bce3e0">
                <td align="center">
                    <strong>Ракеты</strong>
                </td>
                <td align="center">
                    <strong>Радар</strong>
                </td>
            </tr>
            >
            <xsl:for-each select="wp:planes/wp:plane">
                <tr bgcolor="#F5F5F5">
                    <td>
                        <xsl:value-of select="@model"/>
                    </td>
                    <td align="center">
                        <xsl:value-of select="wp:origin"/>
                    </td>
                    <td align="center">
                        <xsl:value-of select="wp:chars/wp:type"/>
                    </td>
                    <td align="center">
                        <xsl:value-of select="wp:chars/wp:spotsNumber"/>
                    </td>
                    <td align="center">
                        <xsl:value-of select="wp:chars/wp:ammunition/wp:rocketsNumber"/>
                        <xsl:if test="not(wp:chars/wp:ammunition/wp:rocketsNumber)">0</xsl:if>
                    </td>
                    <td align="center">
                        <xsl:if test="wp:chars/wp:ammunition/wp:hasRadar='true'">+</xsl:if>
                        <xsl:if test="not(wp:chars/wp:ammunition/wp:hasRadar)">-</xsl:if>
                    </td>
                    <td align="center">
                        <xsl:value-of select="wp:parameters/wp:length"/>
                        <xsl:value-of select="wp:parameters/wp:length/@unit"/>
                        <xsl:if test="wp:parameters/wp:length[not(@unit)]">м</xsl:if>

                    </td>
                    <td align="center">
                        <xsl:value-of select="wp:parameters/wp:width"/>
                        <xsl:value-of select="wp:parameters/wp:width/@unit"/>
                        <xsl:if test="wp:parameters/wp:width[not(@unit)]">м</xsl:if>
                    </td>
                    <td align="center">
                        <xsl:value-of select="wp:parameters/wp:height"/>
                        <xsl:value-of select="wp:parameters/wp:height/@unit"/>
                        <xsl:if test="wp:parameters/wp:height[not(@unit)]">м</xsl:if>
                    </td>
                    <td align="center">
                        <xsl:value-of select="wp:price"/>
                        <xsl:value-of select="wp:price/@unit"/>
                        <xsl:if test="wp:price[not(@unit)]">$</xsl:if>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>