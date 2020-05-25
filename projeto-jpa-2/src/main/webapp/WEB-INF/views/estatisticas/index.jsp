<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:import url="../template/top.jsp" />
<div class="col-sm-8">
	<div class="panel panel-default">
		<div class="panel-heading">Estatísticas</div>
		<div class="panel-body">
			<div class="container">
				<div class="col-sm-8">
					<a href="<c:url value="/estatisticas/limpar"/>">Limpar</a>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Hit</th>
								<th>Miss</th>
								<th>Número de Conexões</th>
								<th>Número de Transações</th>
							</tr>
						</thead>
						<tbody>
							<tr>
							    <td>${statistics.queryCacheHitCount}</td> <!-- consultou no cache -->
							    <td>${statistics.queryCacheMissCount}</td> <!-- consultou no banco -->
							    <td>${statistics.connectCount}</td> <!-- número mínimo de conexões -->
							    <td>${statistics.transactionCount}</td> <!-- número de transações -->
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<c:import url="../template/down.jsp" />
