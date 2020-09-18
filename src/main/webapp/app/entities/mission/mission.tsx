import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './mission.reducer';
import { IMission } from 'app/shared/model/mission.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMissionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Mission = (props: IMissionProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { missionList, match, loading } = props;
  return (
    <div>
      <h2 id="mission-heading">
        <Translate contentKey="touchbaseApp.mission.home.title">Missions</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="touchbaseApp.mission.home.createLabel">Create new Mission</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {missionList && missionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.mission.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.mission.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.mission.scoreReward">Score Reward</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.mission.missonType">Misson Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {missionList.map((mission, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mission.id}`} color="link" size="sm">
                      {mission.id}
                    </Button>
                  </td>
                  <td>{mission.name}</td>
                  <td>{mission.description}</td>
                  <td>{mission.scoreReward}</td>
                  <td>
                    <Translate contentKey={`touchbaseApp.MissionType.${mission.missonType}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mission.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mission.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mission.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="touchbaseApp.mission.home.notFound">No Missions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ mission }: IRootState) => ({
  missionList: mission.entities,
  loading: mission.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Mission);
