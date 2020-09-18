import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './base-join.reducer';
import { IBaseJoin } from 'app/shared/model/base-join.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBaseJoinProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BaseJoin = (props: IBaseJoinProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { baseJoinList, match, loading } = props;
  return (
    <div>
      <h2 id="base-join-heading">
        <Translate contentKey="touchbaseApp.baseJoin.home.title">Base Joins</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="touchbaseApp.baseJoin.home.createLabel">Create new Base Join</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {baseJoinList && baseJoinList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.baseJoin.baseJoin">Base Join</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.baseJoin.base">Base</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {baseJoinList.map((baseJoin, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${baseJoin.id}`} color="link" size="sm">
                      {baseJoin.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`touchbaseApp.BaseJoinAction.${baseJoin.baseJoin}`} />
                  </td>
                  <td>{baseJoin.baseId ? <Link to={`base/${baseJoin.baseId}`}>{baseJoin.baseId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${baseJoin.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${baseJoin.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${baseJoin.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="touchbaseApp.baseJoin.home.notFound">No Base Joins found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ baseJoin }: IRootState) => ({
  baseJoinList: baseJoin.entities,
  loading: baseJoin.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BaseJoin);
