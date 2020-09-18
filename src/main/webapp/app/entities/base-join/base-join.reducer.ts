import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBaseJoin, defaultValue } from 'app/shared/model/base-join.model';

export const ACTION_TYPES = {
  FETCH_BASEJOIN_LIST: 'baseJoin/FETCH_BASEJOIN_LIST',
  FETCH_BASEJOIN: 'baseJoin/FETCH_BASEJOIN',
  CREATE_BASEJOIN: 'baseJoin/CREATE_BASEJOIN',
  UPDATE_BASEJOIN: 'baseJoin/UPDATE_BASEJOIN',
  DELETE_BASEJOIN: 'baseJoin/DELETE_BASEJOIN',
  RESET: 'baseJoin/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBaseJoin>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BaseJoinState = Readonly<typeof initialState>;

// Reducer

export default (state: BaseJoinState = initialState, action): BaseJoinState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BASEJOIN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BASEJOIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BASEJOIN):
    case REQUEST(ACTION_TYPES.UPDATE_BASEJOIN):
    case REQUEST(ACTION_TYPES.DELETE_BASEJOIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BASEJOIN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BASEJOIN):
    case FAILURE(ACTION_TYPES.CREATE_BASEJOIN):
    case FAILURE(ACTION_TYPES.UPDATE_BASEJOIN):
    case FAILURE(ACTION_TYPES.DELETE_BASEJOIN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASEJOIN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASEJOIN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BASEJOIN):
    case SUCCESS(ACTION_TYPES.UPDATE_BASEJOIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BASEJOIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/base-joins';

// Actions

export const getEntities: ICrudGetAllAction<IBaseJoin> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BASEJOIN_LIST,
  payload: axios.get<IBaseJoin>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBaseJoin> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BASEJOIN,
    payload: axios.get<IBaseJoin>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBaseJoin> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BASEJOIN,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBaseJoin> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BASEJOIN,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBaseJoin> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BASEJOIN,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
